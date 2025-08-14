package com.roys.wolvnotekmp.presentation.util

sealed class Screen(val route: String) {
    object AuthScreen: Screen("auth_screen")
    object SettingPasswordScreen: Screen("setting_password_screen")
    object MainScreen: Screen(route = "main_screen")
    object CreateCheckedListScreen: Screen(route = "create_checked_list_screen")
    object CreateNoteScreen: Screen(route = "create_note_screen")
    object CreateSalaryScreen: Screen(route = "create_salary_screen")
    object CreateDrawScreen: Screen(route = "create_draw_screen")
}