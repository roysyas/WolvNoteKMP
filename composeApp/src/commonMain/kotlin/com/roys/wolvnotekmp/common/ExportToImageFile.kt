package com.roys.wolvnotekmp.common

import androidx.compose.ui.unit.IntSize
import com.roys.wolvnotekmp.domain.model.PathData

expect fun exportToImageFile(intSize: IntSize, title: String, paths: List<PathData>): String