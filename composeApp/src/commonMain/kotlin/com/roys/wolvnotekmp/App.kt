package com.roys.wolvnotekmp

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.roys.wolvnotekmp.presentation.util.theme.WolvNoteTheme
import com.roys.wolvnotekmp.presentation.util.AppNavHost
import com.roys.wolvnotekmp.presentation.util.ObserveAsEvents
import com.roys.wolvnotekmp.presentation.util.SnackBarController
import com.roys.wolvnotekmp.presentation.util.composableicon.BackIcon
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    WolvNoteTheme {
        val navController = rememberNavController()
        val scope = rememberCoroutineScope()
        val snackBarHostState = remember { SnackbarHostState() }
        ObserveAsEvents(
            flow = SnackBarController.events,
            snackBarHostState
        ) { event ->
            scope.launch {
                snackBarHostState.currentSnackbarData?.dismiss()
                snackBarHostState.showSnackbar(
                    message = event.message,
                    duration = SnackbarDuration.Long
                )
            }
        }

        if(getPlatform().name.contains("Java")){
            Scaffold(
                topBar = { TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigateUp()
                        }) {
                            Icon(
                                imageVector = BackIcon(),
                                contentDescription = "navigation",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    title = { null }
                ) },
                contentWindowInsets = WindowInsets.safeDrawing,
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackBarHostState
                    )
                }
            ) { innerPadding ->
                AppNavHost(navController, innerPadding)
            }
        }else{
            Scaffold(
                contentWindowInsets = WindowInsets.safeDrawing,
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackBarHostState
                    )
                }
            ) { innerPadding ->
                AppNavHost(navController, innerPadding)
            }
        }
    }
}