package com.roys.wolvnotekmp.presentation.note.draw.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.roys.wolvnotekmp.common.drawPath
import com.roys.wolvnotekmp.domain.model.PathData
import com.roys.wolvnotekmp.presentation.note.draw.DrawEvent

@Composable
fun DrawView(
    modifier: Modifier,
    paths: List<PathData>,
    currentPath: PathData?,
    onAction: (DrawEvent)-> Unit
){
    Canvas(
        modifier = modifier
            .clipToBounds()
            .border(width = 1.dp, color = MaterialTheme.colorScheme.primary)
            .background(color = MaterialTheme.colorScheme.secondary)
            .pointerInput(true){
                detectDragGestures(
                    onDragStart = { offset ->
                        onAction(DrawEvent.OnNewPathStart(offset))
                    },
                    onDragEnd = {
                        onAction(DrawEvent.OnPathEnd)
                    },
                    onDrag = {change, offset ->
                        onAction(DrawEvent.OnDraw(change.position))
                    },
                    onDragCancel = {
                        onAction(DrawEvent.OnPathEnd)
                    }
                )
            }
            .onSizeChanged{
                onAction(DrawEvent.OnGetSize(it))
            }
    ) {
        paths.fastForEach { pathData ->
            drawPath(
                path = pathData.path,
                color = pathData.color,
                thickness = pathData.weight
            )
        }
        currentPath?.let {
            drawPath(
                path = it.path,
                color = it.color,
                thickness = it.weight
            )
        }
    }
}