package com.roys.wolvnotekmp.presentation.note.salarycalculation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.roys.wolvnotekmp.common.Constants
import com.roys.wolvnotekmp.presentation.note.component.InputTitle
import com.roys.wolvnotekmp.presentation.note.salarycalculation.component.InputSalary
import com.roys.wolvnotekmp.presentation.util.composableicon.SaveIcon
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import wolvnotekmp.composeapp.generated.resources.Res
import wolvnotekmp.composeapp.generated.resources.salary_result
import wolvnotekmp.composeapp.generated.resources.save

@Composable
fun CreateSalaryScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: SalaryViewModel = koinViewModel()
){
    val state by viewModel.state.collectAsState()
    val save = stringResource(Res.string.save)
    val salaryResult = stringResource(Res.string.salary_result)

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
    ){
        if(state.noteTitle.isEmpty()){
            InputTitle(
                onClick = {
                    viewModel.handleEvent(SalaryEvent.TitleUpdate(it))
                }
            )
        }else{
            if(state.notes.isEmpty()){
                InputSalary(
                    onClick = {
                        viewModel.handleEvent(SalaryEvent.Calculate(it))
                    }
                )
            }else{
                Column {
                    Text(
                        text = state.noteTitle,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.primary
                    )
                    if(state.createDate.isNotEmpty()){
                        Text(
                            text = "create: "+state.createDate,
                            fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.End),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                    }
                    if(state.lastEditDate!=null){
                        Text(
                            text = "last edit: "+ state.lastEditDate,
                            fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.End),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(rememberScrollState()),
                        value = state.notes,
                        onValueChange = {
                            viewModel.handleEvent(SalaryEvent.ContentUpdate(it))
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrectEnabled = true,
                            keyboardType = KeyboardType.Text
                        ),
                        label = {
                            Text(
                                text = salaryResult,
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    IconButton(
                        modifier = Modifier.align(Alignment.End),
                        onClick = {
                            viewModel.handleEvent(SalaryEvent.InsertNote)
                        }
                    ) {
                        Icon(
                            imageVector = SaveIcon(),
                            contentDescription = save,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}