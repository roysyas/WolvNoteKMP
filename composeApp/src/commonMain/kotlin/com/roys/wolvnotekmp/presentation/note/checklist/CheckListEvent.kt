package com.roys.wolvnotekmp.presentation.note.checklist

import com.roys.wolvnotekmp.domain.model.CheckListItem

sealed interface CheckListEvent {
    data class AddItem(val checkListItem: CheckListItem): CheckListEvent
    data class RemoveItem(val checkListItem: CheckListItem): CheckListEvent
    data class TitleUpdate(val title: String): CheckListEvent
    data object InsertNote: CheckListEvent
    data class CheckItem(val checkListItem: CheckListItem): CheckListEvent
}