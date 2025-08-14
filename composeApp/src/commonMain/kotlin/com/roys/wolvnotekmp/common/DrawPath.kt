package com.roys.wolvnotekmp.common

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.abs
fun DrawScope.drawPath(
    path: List<Offset>,
    color: Color,
    thickness: Float,
    scale: Float? = null
) {
    var scaledPath = path
    var scaledThickness = thickness
    if(scale != null){
        scaledThickness = thickness * scale
        scaledPath = path.map {
            Offset(it.x * scale, it.y * scale)
        }
    }

    val smoothedPathResize = Path().apply {
        if(scaledPath.isNotEmpty()) {
            moveTo(scaledPath.first().x, scaledPath.first().y)

            val smoothness = 5
            for(i in 1..scaledPath.lastIndex) {
                val from = scaledPath[i - 1]
                val to = scaledPath[i]
                val dx = abs(from.x - to.x)
                val dy = abs(from.y - to.y)
                if(dx >= smoothness || dy >= smoothness) {
                    quadraticTo(
                        x1 = (from.x + to.x) / 2f,
                        y1 = (from.y + to.y) / 2f,
                        x2 = to.x,
                        y2 = to.y
                    )
                }
            }
        }
    }
    drawPath(
        path = smoothedPathResize,
        color = color,
        style = Stroke(
            width = scaledThickness,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )
}