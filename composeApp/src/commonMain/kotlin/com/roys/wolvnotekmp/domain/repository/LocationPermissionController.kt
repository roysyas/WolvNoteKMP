package com.roys.wolvnotekmp.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface LocationPermissionController {
    val status: StateFlow<Boolean>
    fun requestPermission()
}