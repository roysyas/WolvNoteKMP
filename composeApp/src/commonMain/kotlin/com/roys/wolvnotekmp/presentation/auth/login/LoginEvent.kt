package com.roys.wolvnotekmp.presentation.auth.login

sealed interface LoginEvent {
    data object CheckPassword: LoginEvent
    data class PasswordUpdate(val password: String): LoginEvent
}