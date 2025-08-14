package com.roys.wolvnotekmp.presentation.note.draw

import androidx.compose.ui.graphics.Color
import com.roys.wolvnotekmp.domain.model.DrawData
import com.roys.wolvnotekmp.domain.model.PathData

data class DrawState (
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isEdit: Boolean = false,
    val selectedColor: Color = Color.Black,
    val selectedWeight: Float = 10f,
    val currentPath: PathData? = null,
    val drawData: DrawData = DrawData()
)