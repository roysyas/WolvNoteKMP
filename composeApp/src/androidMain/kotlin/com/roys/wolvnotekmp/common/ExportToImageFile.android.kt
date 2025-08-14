package com.roys.wolvnotekmp.common

import android.graphics.Bitmap
import android.os.Environment
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.unit.IntSize
import com.roys.wolvnotekmp.common.ExportToImageFileHelper.saveBitmap
import com.roys.wolvnotekmp.domain.model.PathData
import java.io.File
import java.io.FileOutputStream
import kotlin.use

actual fun exportToImageFile(
    intSize: IntSize,
    title: String,
    paths: List<PathData>
): String {
    val titleReplace = title.replace(" ", "")
    val file = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        "${titleReplace}-${System.currentTimeMillis()}.png"
    )

    val fileOutputStream = FileOutputStream(file)
    fileOutputStream.use {outputStream ->
        saveBitmap(intSize,paths).asAndroidBitmap().compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
    }

    return file.path
}