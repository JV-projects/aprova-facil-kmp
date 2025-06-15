package edu.fatec.jvprojects.utils.extensions

import androidx.compose.ui.graphics.ImageBitmap

expect fun decodeByteArrayToImageBitmap(byteArray: ByteArray): ImageBitmap?