package com.roys.wolvnotekmp.presentation.note.salarycalculation

sealed interface SalaryEvent {
    data class TitleUpdate(val title: String): SalaryEvent
    data object InsertNote: SalaryEvent
    data class Calculate(val amount: String): SalaryEvent
    data class ContentUpdate(val content: String): SalaryEvent
}