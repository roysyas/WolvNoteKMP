package com.roys.wolvnotekmp.presentation.note.mainpage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.roys.wolvnotekmp.common.Constants
import com.roys.wolvnotekmp.presentation.note.mainpage.component.CustomFloatingButton
import com.roys.wolvnotekmp.presentation.note.mainpage.component.LocationPermission
import com.roys.wolvnotekmp.presentation.note.mainpage.component.NoteItem
import com.roys.wolvnotekmp.presentation.util.Screen
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import wolvnotekmp.composeapp.generated.resources.Res
import wolvnotekmp.composeapp.generated.resources.empty_notes

@Composable
fun MainScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val emptyNotes = stringResource(Res.string.empty_notes)

    LocationPermission(
        onGranted = {
            viewModel.handleEvent(HomeEvent.RequestPermission(it))
        }
    )

    LaunchedEffect(key1 = Unit) {
        navController.currentBackStackEntry?.savedStateHandle?.getStateFlow(Constants.REFRESH, false)?.collect { result->
            if(result){
                viewModel.handleEvent(HomeEvent.OnRefresh(true))
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(8.dp)
    ) {
        if(state.noteList.isEmpty()){
            Text(
                text = emptyNotes,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        if(state.error.isNotBlank()){
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Column {
            state.currentWeather?.let {
                Column(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 8.dp, 8.dp)
                        .align(Alignment.End)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = it.temperature,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        modifier = Modifier
                            .align(Alignment.End)
                    )
                    Text(
                        text = it.weather,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        modifier = Modifier
                            .align(Alignment.End)
                    )
                }
            }
            if (state.noteList.isNotEmpty()) {
                NoteItem(
                    listNoteTable = state.noteList,
                    navController = navController,
                    onDelete = { data ->
                        viewModel.handleEvent(HomeEvent.OnDelete(data))
                    }
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(0.dp,0.dp,8.dp,8.dp)
                .align(Alignment.BottomEnd)
        ){
            CustomFloatingButton(
                state,
                onClick = { result ->
                    when(result){
                        Constants.CATEGORY_CHECKLIST ->{
                            navController.navigate(Screen.CreateCheckedListScreen.route)
                        }
                        Constants.CATEGORY_NOTE ->{
                            navController.navigate(Screen.CreateNoteScreen.route)
                        }
                        Constants.CATEGORY_SALARY ->{
                            navController.navigate(Screen.CreateSalaryScreen.route)
                        }

                        Constants.CATEGORY_DRAW ->{
                            navController.navigate(Screen.CreateDrawScreen.route)
                        }
                    }
                },
                onToggle = {
                    viewModel.handleEvent(HomeEvent.OnToggle(it))
                }
            )
        }
    }
}