package com.roys.wolvnotekmp.common

import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.unit.IntSize
import com.roys.wolvnotekmp.domain.model.PathData
import java.io.File
import javax.imageio.ImageIO

actual fun exportToImageFile(
    intSize: IntSize,
    title: String,
    paths: List<PathData>
): String {
    val titleReplace = title.replace(" ", "")
    val picturesDir = File(System.getProperty("user.home"), "Pictures")

    if (!picturesDir.exists()) {
        picturesDir.mkdirs()
    }

    val file = File(picturesDir, "${titleReplace}-${System.currentTimeMillis()}.jpg")

    val awtImage = ExportToImageFileHelper
        .saveBitmap(intSize, paths)
        .toAwtImage()

    ImageIO.write(awtImage, "jpg", file)

    return file.absolutePath
}