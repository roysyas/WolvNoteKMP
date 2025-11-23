package com.roys.wolvnotekmp.presentation.note.mainpage.component

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.roys.wolvnotekmp.common.Constants
import com.roys.wolvnotekmp.domain.model.NoteTable
import com.roys.wolvnotekmp.presentation.note.component.ConfirmationDialog
import com.roys.wolvnotekmp.presentation.util.Screen
import com.roys.wolvnotekmp.presentation.util.composableicon.DeleteIcon
import org.jetbrains.compose.resources.stringResource
import wolvnotekmp.composeapp.generated.resources.Res
import wolvnotekmp.composeapp.generated.resources.delete_message

@Composable
fun NoteItem(
    listNoteTable: List<NoteTable>,
    navController: NavController,
    onDelete: (Int?)-> Unit
) {
    val confirmationMessage = stringResource(Res.string.delete_message)
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var deletedItem: Int? by rememberSaveable { mutableStateOf(0) }

    when (showDialog) {
        true -> ConfirmationDialog(
            message = confirmationMessage,
            imageVector = DeleteIcon(),
            onDismiss = {
                showDialog = false
                deletedItem = NoteTable().noteId
            },
            onConfirm = {
                showDialog = false
                onDelete(deletedItem)
                deletedItem = NoteTable().noteId
            }
        )
        false -> {}
    }


    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {

        items(listNoteTable){ item ->
            when(item.noteCategory){
                Constants.CATEGORY_CHECKLIST ->{
                    CheckListComponent(
                        modifier = Modifier.combinedClickable(
                            onClick = { navController.navigate(Screen.CreateCheckedListScreen.route + "?${item.noteId}") },
                            onLongClick = {
                                showDialog = true
                                deletedItem = item.noteId
                            }
                        ),
                        noteContent = item.noteContent,
                        noteTitle = item.noteTitle
                    )
                }

                Constants.CATEGORY_NOTE ->{
                    NoteComponent(
                        modifier = Modifier.combinedClickable(
                            onClick = { navController.navigate(Screen.CreateNoteScreen.route + "?${item.noteId}") },
                            onLongClick = {
                                showDialog = true
                                deletedItem = item.noteId
                            }
                        ),
                        noteContent = item.noteContent,
                        noteTitle = item.noteTitle
                    )
                }

                Constants.CATEGORY_SALARY ->{
                    NoteComponent(
                        modifier = Modifier.combinedClickable(
                            onClick = { navController.navigate(Screen.CreateSalaryScreen.route + "?${item.noteId}") },
                            onLongClick = {
                                showDialog = true
                                deletedItem = item.noteId
                            }
                        ),
                        noteContent = item.noteContent,
                        noteTitle = item.noteTitle
                    )
                }

                Constants.CATEGORY_DRAW ->{
                    DrawComponent(
                        modifier = Modifier.combinedClickable(
                            onClick = { navController.navigate(Screen.CreateDrawScreen.route + "?${item.noteId}") },
                            onLongClick = {
                                showDialog = true
                                deletedItem = item.noteId
                            }
                        ),
                        noteTitle = item.noteTitle,
                        noteContent = item.noteContent
                    )
                }
            }
        }
    }
}