package com.roys.wolvnotekmp.presentation.util.composableicon

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Composable
fun CloseIcon(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "Arrow_drop_down",
            defaultWidth = 36.dp,
            defaultHeight = 36.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000))
            ) {
                moveTo(480f, 600f)
                lineTo(280f, 400f)
                horizontalLineToRelative(400f)
                close()
            }
        }.build()
    }
}