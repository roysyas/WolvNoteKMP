package com.roys.wolvnotekmp.presentation.note.draw

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.roys.wolvnotekmp.common.Constants
import com.roys.wolvnotekmp.presentation.note.component.InputTitle
import com.roys.wolvnotekmp.presentation.note.draw.component.DrawMenu
import com.roys.wolvnotekmp.presentation.note.draw.component.DrawView
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CreateDrawScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: DrawViewModel = koinViewModel()
){
    val state by viewModel.state.collectAsState()

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
        if(state.drawData.noteTitle.isEmpty()){
            InputTitle(
                onClick = {
                    viewModel.handleEvent(DrawEvent.TitleUpdate(it))
                }
            )
        }else{
            Column {
                Text(
                    text = state.drawData.noteTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.primary
                )
                if(state.drawData.createDate.isNotEmpty()){
                    Text(
                        text = "create: "+state.drawData.createDate,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.End),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
                if(state.drawData.lastEditDate!=null){
                    Text(
                        text = "last edit: "+ state.drawData.lastEditDate,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.End),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                DrawView(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    paths = state.drawData.contentData.pathDataList,
                    currentPath = state.currentPath,
                    onAction = viewModel::handleEvent
                )
                Spacer(modifier = Modifier.height(8.dp))
                DrawMenu(
                    paths = state.drawData.contentData.pathDataList,
                    selectedColor = state.selectedColor,
                    colors = Constants.allColors,
                    selectedWeight = state.selectedWeight,
                    weights = Constants.allWeight,
                    onClick = {
                        viewModel.handleEvent(it)
                    },
                    onSelectColor = {
                        viewModel.handleEvent(DrawEvent.OnSelectColor(it))
                    },
                    onSelectWeight = {
                        viewModel.handleEvent(DrawEvent.OnSelectWeight(it))
                    }
                )
            }
        }
    }
}