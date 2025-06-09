package edu.fatec.jvprojects.utils.extensions

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.toCValues
import kotlinx.cinterop.usePinned
import org.jetbrains.skia.Image
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes
import platform.UIKit.UIImage
import platform.Foundation.create

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual fun decodeByteArrayToImageBitmap(byteArray: ByteArray): ImageBitmap? {
    val nsData = byteArray.usePinned { pinned ->
        NSData.create(bytes = pinned.addressOf(0), length = byteArray.size.toULong())
    }
    val uiImage = UIImage.imageWithData(nsData)
    // Para converter UIImage para ImageBitmap, você pode precisar de uma função auxiliar ou biblioteca
    // como https://github.com/JetBrains/skia-canvas/issues/140#issuecomment-991753173
    // ou uma abordagem mais direta se estiver usando o Skia diretamente (Compose usa Skia).
    // Uma maneira comum é usar o Skia diretamente para criar uma imagem a partir de bytes.
    return try {
        Image.makeFromEncoded(byteArray).toComposeImageBitmap()
    } catch (e: Exception) {
        println("Erro ao converter ByteArray para ImageBitmap: ${e.message}")
        null
    }
}