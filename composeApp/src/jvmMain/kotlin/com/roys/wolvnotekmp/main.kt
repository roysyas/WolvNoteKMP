package com.roys.wolvnotekmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.roys.wolvnotekmp.di.initKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "WolvNoteKMP",
    ) {
        App()
    }
}