package com.roys.wolvnotekmp.presentation.note.mainpage.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.roys.wolvnotekmp.common.drawPath
import com.roys.wolvnotekmp.common.toDpSize
import com.roys.wolvnotekmp.domain.model.DrawContentData
import kotlinx.serialization.json.Json
import kotlin.math.min

@Composable
fun DrawComponent(
    modifier: Modifier,
    noteTitle: String,
    noteContent: String
) {
    var data by remember { mutableStateOf(DrawContentData()) }
    data = Json.decodeFromString(noteContent)
    val paths = data.pathDataList

    val thumbnailSize = IntSize(592,951).toDpSize()
    val originalSize = data.size?.toDpSize()
    val scaleX = thumbnailSize.width / originalSize!!.width
    val scaleY = thumbnailSize.height / originalSize.height
    val scale = min(scaleX, scaleY)

    Card (
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier.padding(4.dp)
    ){
        Column {
            Canvas(
                modifier = modifier.size(thumbnailSize)
            ) {
                drawRect(color = Color.White, topLeft = Offset.Zero, size = size)
                paths.fastForEach { pathData ->
                    drawPath(
                        path = pathData.path,
                        color = pathData.color,
                        thickness = pathData.weight,
                        scale = scale
                    )
                }
            }
            NoteTitleItemComponent(noteTitle)
        }
    }
}

