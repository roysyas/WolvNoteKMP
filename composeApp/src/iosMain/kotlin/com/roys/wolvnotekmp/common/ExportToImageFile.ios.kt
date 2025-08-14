package com.roys.wolvnotekmp.common

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.unit.IntSize
import com.roys.wolvnotekmp.domain.model.PathData
import platform.Foundation.NSDate
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask
import platform.Foundation.timeIntervalSince1970
import com.roys.wolvnotekmp.common.ExportToImageFileHelper.saveBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.usePinned
import org.jetbrains.skia.Image
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes
import platform.Foundation.writeToFile
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import kotlinx.cinterop.addressOf


actual fun exportToImageFile(
    intSize: IntSize,
    title: String,
    paths: List<PathData>
): String {
    val titleReplace = title.replace(" ", "")
    val fileName = "${titleReplace}-${NSDate().timeIntervalSince1970()}.jpg"
    val pathsDir = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, true).first() as String
    val filePath = "$pathsDir/$fileName"

    val image = saveBitmap(intSize, paths).toUIImage()
    val imageData = UIImageJPEGRepresentation(image, 1.0)
    (imageData as NSData).writeToFile(filePath, true)

    return filePath
}

@OptIn(ExperimentalForeignApi::class)
fun ImageBitmap.toUIImage(): UIImage {
    // Convert ImageBitmap → Skia Image
    val skiaImage = Image.makeFromBitmap(this.asSkiaBitmap())

    // Encode Skia Image to PNG bytes
    val pngBytes = skiaImage.encodeToData()?.bytes ?: return UIImage()

    // Convert bytes → NSData
    val nsData = pngBytes.usePinned {
        NSData.dataWithBytes(it.addressOf(0), pngBytes.size.toULong())
    }

    // Create UIImage from NSData
    return UIImage(nsData)
}