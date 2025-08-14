package com.roys.wolvnotekmp.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roys.wolvnotekmp.common.PreferenceDataStore
import com.roys.wolvnotekmp.common.Resource
import com.roys.wolvnotekmp.common.UiText
import com.roys.wolvnotekmp.common.SecureStorage
import com.roys.wolvnotekmp.domain.usecase.password.GetPasswordUseCase
import com.roys.wolvnotekmp.presentation.util.SnackBarController
import com.roys.wolvnotekmp.presentation.util.SnackBarError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import wolvnotekmp.composeapp.generated.resources.Res
import wolvnotekmp.composeapp.generated.resources.wrong_password

class LoginViewModel (
    private val getPasswordUseCase: GetPasswordUseCase,
    private val preferenceDataStore: PreferenceDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    init {
        getPassword()
    }

    fun handleEvent(viewEvent: LoginEvent){
        when(viewEvent){
            is LoginEvent.CheckPassword -> checkPassword()
            is LoginEvent.PasswordUpdate -> passwordUpdate(viewEvent.password)
        }
    }

    private fun passwordUpdate(password: String){
        _state.update {
            it.copy(
                passwordInputText = password
            )
        }
    }

    private fun checkPassword(){
        _state.value.password?.let { password ->
            viewModelScope.launch {
                val secureStorage = SecureStorage(preferenceDataStore)
                val key = secureStorage.checkKey()
                val securePassword = secureStorage.decrypt(password, key)
                if (_state.value.passwordInputText == securePassword) {
                    _state.update {
                        it.copy(
                            isSuccess = true
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            errorText = UiText.StringResourceId(Res.string.wrong_password)
                        )
                    }
                }
            }
        }
    }

    private fun getPassword() {
        getPasswordUseCase.invoke().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }

                is Resource.Success -> {
                    if (result.data == null) {
                        _state.update {
                            it.copy(
                                isEmpty = true
                            )
                        }
                    } else {
                        _state.update {
                            it.copy(
                                password = result.data.password,
                                hint = result.data.hint
                            )
                        }
                    }
                }

                is Resource.Error -> {
                    viewModelScope.launch {
                        SnackBarController.sendEvent(
                            event = SnackBarError(
                                message = result.message ?: "An unexpected error occurred"
                            )
                        )
                    }
                }
            }

        }.launchIn(viewModelScope)
    }
}