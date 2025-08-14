package com.roys.wolvnotekmp.presentation.auth.settingpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roys.wolvnotekmp.common.Constants
import com.roys.wolvnotekmp.common.PreferenceDataStore
import com.roys.wolvnotekmp.common.Resource
import com.roys.wolvnotekmp.common.UiText
import com.roys.wolvnotekmp.common.SecureStorage
import com.roys.wolvnotekmp.data.database.PasswordTable
import com.roys.wolvnotekmp.domain.usecase.password.InsertPasswordUseCase
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
import wolvnotekmp.composeapp.generated.resources.empty_hint
import wolvnotekmp.composeapp.generated.resources.empty_password

class SettingPasswordViewModel (
    private val insertPasswordUseCase: InsertPasswordUseCase,
    private val preferenceDataStore: PreferenceDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(SettingPasswordsState())
    val state: StateFlow<SettingPasswordsState> = _state.asStateFlow()

    fun handleEvent(viewEvent: SettingPasswordEvent){
        when(viewEvent){
            is SettingPasswordEvent.InsertPassword -> saveCredential()
            is SettingPasswordEvent.HintUpdate -> hintUpdate(viewEvent.hint)
            is SettingPasswordEvent.PasswordUpdate -> passwordUpdate(viewEvent.password)
        }
    }

    private fun passwordUpdate(password: String){
        _state.update {
            it.copy(
                passwordInputText = password
            )
        }
    }

    private fun hintUpdate(hint: String){
        _state.update {
            it.copy(
                hintInputText = hint
            )
        }
    }

    private fun saveCredential(){
        if(_state.value.passwordInputText.isEmpty()){
            _state.update {
                it.copy(
                    errorText = UiText.StringResourceId(Res.string.empty_password)
                )
            }
        }else if(_state.value.hintInputText.isEmpty()){
            _state.update {
                it.copy(
                    errorText = UiText.StringResourceId(Res.string.empty_hint)
                )
            }
        }else{
            prepareInsertPassword(
                _state.value.passwordInputText,
                _state.value.hintInputText
            )
        }
    }

    private fun prepareInsertPassword(password:String, hint: String){
        viewModelScope.launch {
            val secureStorage = SecureStorage(preferenceDataStore)
            val key = secureStorage.checkKey()
            val securePassword = secureStorage.encrypt(password, key)

            val passwordTable = PasswordTable(
                passwordId = null,
                password = securePassword,
                hint = hint
            )
            insertPassword(passwordTable)
        }
    }

    private fun insertPassword(passwordTable: PasswordTable) {
        insertPasswordUseCase.invoke(passwordTable).onEach { result ->
            when(result){
                is Resource.Loading ->{
                    _state.value = SettingPasswordsState(isLoading = true)
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
                is Resource.Success -> {
                    _state.value = SettingPasswordsState(isSuccess = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}