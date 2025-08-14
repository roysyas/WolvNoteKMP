package com.roys.wolvnotekmp.presentation.util.composableicon

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp


@Composable
fun BackIcon(): ImageVector {
    return remember {
        ImageVector.Builder(
                name = "Arrow_back_ios",
                defaultWidth = 36.dp,
                defaultHeight = 36.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000))
                ) {
                    moveTo(400f, 880f)
                    lineTo(0f, 480f)
                    lineToRelative(400f, -400f)
                    lineToRelative(71f, 71f)
                    lineToRelative(-329f, 329f)
                    lineToRelative(329f, 329f)
                    close()
                }
            }.build()


    }
}