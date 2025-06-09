package edu.fatec.jvprojects.utils.extensions

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image

actual fun decodeByteArrayToImageBitmap(byteArray: ByteArray): ImageBitmap? {
    return Image.makeFromEncoded(byteArray).toComposeImageBitmap()
}