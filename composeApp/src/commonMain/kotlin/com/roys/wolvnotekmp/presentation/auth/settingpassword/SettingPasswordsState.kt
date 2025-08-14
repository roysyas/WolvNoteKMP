package com.roys.wolvnotekmp.presentation.auth.settingpassword

import com.roys.wolvnotekmp.common.UiText

data class SettingPasswordsState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val passwordInputText: String = "",
    val hintInputText: String = "",
    val errorText: UiText? = null
)