package com.roys.wolvnotekmp.presentation.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.roys.wolvnotekmp.presentation.util.Screen
import org.jetbrains.compose.resources.stringResource
import wolvnotekmp.composeapp.generated.resources.Res
import wolvnotekmp.composeapp.generated.resources.hint
import wolvnotekmp.composeapp.generated.resources.input_password
import wolvnotekmp.composeapp.generated.resources.submit

@Composable
fun LoginComponent(
    paddingValues: PaddingValues,
    passwordInput: String,
    hint: String,
    errorText: String?,
    isSuccess: Boolean,
    navController: NavController,
    onClick:()-> Unit,
    onChange:(String)-> Unit
){
    val inputPassword = stringResource(Res.string.input_password)
    val submit = stringResource(Res.string.submit)
    val hint = stringResource(Res.string.hint)+" "+hint

    if(isSuccess){
        navController.navigate(Screen.MainScreen.route){
            popUpTo(Screen.AuthScreen.route){
                inclusive = true
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = passwordInput,
            onValueChange = {
                onChange(it)
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrectEnabled = true,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions {
                onClick()
            },
            label = {
                Text(
                    text = inputPassword,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            textStyle = TextStyle(color = MaterialTheme.colorScheme.primary)
        )
        Text(
            text = hint,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.background(color = MaterialTheme.colorScheme.primary).padding(8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        errorText?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                onClick()
            }
        ) {
            Text(submit)
        }
    }

}