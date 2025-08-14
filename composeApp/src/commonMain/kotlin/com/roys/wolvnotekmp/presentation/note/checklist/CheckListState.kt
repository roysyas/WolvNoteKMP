package com.roys.wolvnotekmp.presentation.note.checklist

import com.roys.wolvnotekmp.domain.model.CheckListData

data class CheckListState (
    val checkListData: CheckListData = CheckListData(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isEdit: Boolean = false
)