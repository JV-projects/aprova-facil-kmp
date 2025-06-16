package edu.fatec.jvprojects.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLData
import edu.fatec.jvprojects.composables.FilePicker
import edu.fatec.jvprojects.composables.WebViewScreen
import edu.fatec.jvprojects.utils.extensions.decodeByteArrayToImageBitmap
import edu.fatec.jvprojects.utils.extensions.toImageBitmap
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.readBytes
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.launch
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
@Composable
fun TesteScreen(
    link: String
) {

    val url = link

    println("link: $link")
    println("url: $url")


    Column {
        WebViewScreen(url)
    }
}