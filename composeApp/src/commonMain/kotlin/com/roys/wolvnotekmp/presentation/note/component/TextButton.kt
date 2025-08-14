package com.roys.wolvnotekmp.presentation.note.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun TextButton(
    imageVector: ImageVector,
    label: String,
    contentColor: Color,
    onClick:() -> Unit
) {
    Box(
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = onClick)
                .background(
                    shape = RoundedCornerShape(4.dp),
                    color = Color.Transparent
                )
                .border(BorderStroke(2.dp, contentColor),RoundedCornerShape(4.dp))
                .padding(6.dp, 4.dp, 6.dp, 4.dp)
        ) {
            Text(label, color = contentColor)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = imageVector,
                contentDescription = label,
                tint = contentColor
            )
        }
    }
}