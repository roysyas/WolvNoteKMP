package com.roys.wolvnotekmp.presentation.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.roys.wolvnotekmp.common.Constants
import com.roys.wolvnotekmp.presentation.auth.settingpassword.SettingPasswordScreen
import com.roys.wolvnotekmp.presentation.auth.login.AuthScreen
import com.roys.wolvnotekmp.presentation.note.checklist.CreateCheckedListScreen
import com.roys.wolvnotekmp.presentation.note.draw.CreateDrawScreen
import com.roys.wolvnotekmp.presentation.note.mainpage.MainScreen
import com.roys.wolvnotekmp.presentation.note.notetaker.CreateNoteScreen
import com.roys.wolvnotekmp.presentation.note.salarycalculation.CreateSalaryScreen

@Composable
fun AppNavHost(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screen.AuthScreen.route
    ) {
        composable(route = Screen.AuthScreen.route) {
            AuthScreen(paddingValues, navController)
        }
        composable(route = Screen.SettingPasswordScreen.route) {
            SettingPasswordScreen(paddingValues, navController)
        }
        composable(route = Screen.MainScreen.route){
            MainScreen(paddingValues, navController)
        }
        composable(
            route = Screen.CreateCheckedListScreen.route + "?{${Constants.NOTE_ID}}",
            arguments = listOf(
                navArgument(Constants.NOTE_ID) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ){
            CreateCheckedListScreen(paddingValues, navController)
        }
        composable(
            route = Screen.CreateNoteScreen.route + "?{${Constants.NOTE_ID}}",
            arguments = listOf(
                navArgument(Constants.NOTE_ID) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ){
            CreateNoteScreen(paddingValues, navController)
        }
        composable(
            route = Screen.CreateSalaryScreen.route + "?{${Constants.NOTE_ID}}",
            arguments = listOf(
                navArgument(Constants.NOTE_ID) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ){
            CreateSalaryScreen(paddingValues, navController)
        }
        composable(
            route = Screen.CreateDrawScreen.route + "?{${Constants.NOTE_ID}}",
            arguments = listOf(
                navArgument(Constants.NOTE_ID) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ){
            CreateDrawScreen(paddingValues, navController)
        }
    }
}