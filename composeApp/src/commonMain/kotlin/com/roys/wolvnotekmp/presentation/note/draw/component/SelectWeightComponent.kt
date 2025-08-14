package com.roys.wolvnotekmp.presentation.note.draw.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach

@Composable
fun SelectWeightComponent(
    selectedWeight: Float,
    weights: List<Float>,
    onSelectWeight: (Float) -> Unit
) {

    val isDark = isSystemInDarkTheme()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
    ) {
        weights.fastForEach { weight ->
            val isSelected = selectedWeight == weight

            Canvas(
                modifier = Modifier.size(36.dp)
                    .border(width = 1.dp, color = if(isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                    .clickable{onSelectWeight(weight)}
            ) {
                drawLine(
                    color = if(isDark)Color.White else Color.Black,
                    strokeWidth = weight,
                    start = Offset(x = 0.dp.toPx(), y = 36.dp.toPx()/2),
                    end = Offset(x = 36.dp.toPx(), y = 36.dp.toPx()/2)
                )
            }
        }

    }
}