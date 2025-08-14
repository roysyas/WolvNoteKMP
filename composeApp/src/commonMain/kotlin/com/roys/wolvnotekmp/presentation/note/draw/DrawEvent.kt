package com.roys.wolvnotekmp.presentation.note.draw

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize

sealed interface DrawEvent {
    data class TitleUpdate(val title: String): DrawEvent
    data object InsertNote: DrawEvent
    data object InsertAndExport: DrawEvent

    data class OnNewPathStart(val offset: Offset): DrawEvent
    data class OnDraw(val offset: Offset): DrawEvent
    data object OnPathEnd: DrawEvent
    data class OnSelectColor(val color: Color): DrawEvent
    data object OnClearCanvasClick: DrawEvent
    data class OnSelectWeight(val weight: Float): DrawEvent
    data class OnGetSize(val size: IntSize): DrawEvent
}