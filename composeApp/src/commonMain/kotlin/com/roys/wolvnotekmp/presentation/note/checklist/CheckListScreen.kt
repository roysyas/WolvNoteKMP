package com.roys.wolvnotekmp.presentation.note.checklist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.roys.wolvnotekmp.common.Constants
import com.roys.wolvnotekmp.common.DateTimeHelper
import com.roys.wolvnotekmp.domain.model.CheckListItem
import com.roys.wolvnotekmp.presentation.note.component.InputTitle
import com.roys.wolvnotekmp.presentation.note.checklist.component.AddItemView
import com.roys.wolvnotekmp.presentation.note.checklist.component.CheckListItemView
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import wolvnotekmp.composeapp.generated.resources.Res
import wolvnotekmp.composeapp.generated.resources.empty_checklist

@Composable
fun CreateCheckedListScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: CheckListViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()
    val emptyList = stringResource(Res.string.empty_checklist)
    val lazyState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    if (state.isSuccess) {
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set(Constants.REFRESH, true)
        navController.popBackStack()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(8.dp)
    ) {
        if(state.checkListData.noteTitle.isEmpty()){
            InputTitle(
                onClick = {
                    viewModel.handleEvent(CheckListEvent.TitleUpdate(it))
                }
            )
        }else{
            if (state.checkListData.contentData.isEmpty()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = emptyList
                )
            }
            Column{
                Text(
                    text = state.checkListData.noteTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.primary
                )
                if(state.checkListData.createDate.isNotEmpty()){
                    Text(
                        text = "create: "+state.checkListData.createDate,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.End),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
                if(state.checkListData.lastEditDate!=null){
                    Text(
                        text = "last edit: "+ state.checkListData.lastEditDate,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.End),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.weight(1f)) {
                    if (state.checkListData.contentData.isNotEmpty()) {
                        LazyColumn(modifier = Modifier.fillMaxSize(), state = lazyState) {
                            items(state.checkListData.contentData, key = { item -> item.id }) { result ->
                                CheckListItemView(
                                    result,
                                    onRemoveClick = {
                                        viewModel.handleEvent(CheckListEvent.RemoveItem(result))
                                    },
                                    onCheckedChange = {
                                        viewModel.handleEvent(CheckListEvent.CheckItem(result))
                                    }
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                AddItemView(
                    list = state.checkListData.contentData,
                    onClick = { itemText, checked ->
                        val checkListItem = CheckListItem(
                            checked,
                            itemText,
                            DateTimeHelper.getId()
                        )
                        viewModel.handleEvent(CheckListEvent.AddItem(checkListItem))
                        coroutineScope.launch{
                            lazyState.animateScrollToItem(index = state.checkListData.contentData.size)
                        }
                    },
                    insertNoteClick = {
                        viewModel.handleEvent(CheckListEvent.InsertNote)
                    }
                )
            }
        }
    }
}