package com.roys.wolvnotekmp.common

import androidx.compose.ui.graphics.Color

object Constants {
    const val CATEGORY_NOTE = 1
    const val CATEGORY_SALARY = 2
    const val CATEGORY_CHECKLIST = 3
    const val CATEGORY_DRAW = 4
    const val NOTE_ID = "note_id"
    const val REFRESH = "refresh"
    val allColors = listOf(
        Color.Black,
        Color.Red,
        Color.Blue,
        Color.Green,
        Color.Yellow,
        Color.Magenta,
        Color.Cyan
    )
    val allWeight = listOf(
        10f,
        20f,
        30f,
        40f,
        50f,
        60f,
        70f
    )

    const val PROVIDER = "AndroidKeyStore"
    const val KEY_ALIAS = "PrivateSecretKey"
    const val PREFERENCES_NAME = "app_prefs"
    const val PREFERENCES_PROVIDER_KEY = "provider_key"
}