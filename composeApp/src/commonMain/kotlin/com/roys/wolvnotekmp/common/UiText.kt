package com.roys.wolvnotekmp.common

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

sealed interface UiText {
    class StringResourceId(
        val resId: StringResource
    ): UiText

    @Composable
    fun asString(): String{
        return when(this){
            is StringResourceId -> stringResource(resId)
        }
    }
}