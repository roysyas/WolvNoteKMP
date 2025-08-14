package com.roys.wolvnotekmp.presentation.auth.login

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.roys.wolvnotekmp.presentation.util.Screen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    if(state.isEmpty){
        navController.navigate(Screen.SettingPasswordScreen.route){
            popUpTo(Screen.AuthScreen.route){
                inclusive = true
            }
        }
    }else{
        LoginComponent(
            paddingValues = paddingValues,
            passwordInput = state.passwordInputText,
            hint = state.hint,
            navController = navController,
            isSuccess = state.isSuccess,
            errorText = state.errorText?.asString(),
            onClick = {
                viewModel.handleEvent(LoginEvent.CheckPassword)
            },
            onChange = {
                viewModel.handleEvent(LoginEvent.PasswordUpdate(it))
            }
        )
    }
}