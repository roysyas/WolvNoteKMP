package com.roys.wolvnotekmp

import androidx.compose.ui.window.ComposeUIViewController
import com.roys.wolvnotekmp.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}