package com.roys.wolvnotekmp.common

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.util.fastForEach
import com.roys.wolvnotekmp.domain.model.PathData
import kotlin.math.abs

object ExportToImageFileHelper {

    fun saveBitmap(intSize: IntSize, paths: List<PathData>): ImageBitmap{
        val canvasDrawScope = CanvasDrawScope()
        val size = Size(intSize.width.toFloat(), intSize.height.toFloat())
        val bitmap = canvasDrawScope.asBitmap(size){
            drawRect(color = Color.White, topLeft = Offset.Zero, size = size)
            paths.fastForEach{ pathData ->
                canvasDrawScope.drawPath(
                    path = pathData.path,
                    color = pathData.color,
                    thickness = pathData.weight
                )
            }
        }
        return bitmap
    }

    private fun CanvasDrawScope.asBitmap(size: Size, onDraw: DrawScope.()-> Unit): ImageBitmap{
        val bitmap = ImageBitmap(size.width.toInt(), size.height.toInt())
        draw(Density(1f), LayoutDirection.Ltr, Canvas(bitmap), size) { onDraw() }
        return bitmap
    }

    private fun CanvasDrawScope.drawPath(
        path: List<Offset>,
        color: Color,
        thickness: Float
    ){
        val smoothedPath = Path().apply {
            if(path.isNotEmpty()) {
                moveTo(path.first().x, path.first().y)

                val smoothness = 5
                for(i in 1..path.lastIndex) {
                    val from = path[i - 1]
                    val to = path[i]
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
            path = smoothedPath,
            color = color,
            style = Stroke(
                width = thickness,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}