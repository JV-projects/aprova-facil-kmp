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

//    val html = """
//        <html>
//            <body>
//                <h1>Teste testando</h1>
//            </body>
//        </html>
//    """.trimIndent()
//    val webViewState = rememberWebViewStateWithHTMLData(html)

    val url = when (link) {
        "1" -> {
            "file:///home/carjooj/Downloads/Curr%C3%ADculo%20-%20Victor%20Cardoso%20(1).pdf"
        }
        "2" -> {
            "file:///home/carjooj/Downloads/7%20-NOVO%20%20REGISTRO%20DE%20ATENDIMENTO.pdf"
        }
        "3" -> {
            "https://github.com/KevinnZou/compose-webview-multiplatform/tree/main?tab=readme-ov-file"
        }
        else -> {
            "https://klibs.io/"
        }
    }

    val bitmapState = remember { mutableStateOf<ImageBitmap?>(null) }
    val base64 = remember { mutableStateOf<String>("iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAQAAADZc7J/AAAAIGNIUk0AAHomAACAhAAA+gAAAIDoAAB1MAAA6mAAADqYAAAXcJy6UTwAAAHiSURBVHjarZXNShxREIW/tDMwsxiYF9SNTAtxk8nCeYH4GG5GTQgkmRBolYSIP4ivMi4cf2al3W17uFysRXWjo1ZxoS6cc25Xdd26vId9ZMI2u/Id+ZgfjIAP8miKEmCdX4wDRlgxfrJBsG9UFFTRS63DWoHvDvePYNsK7yjImXLLjJyJE1gCtnjgUogpOYUYFRnBdpFyUPwE9OnRdQKK6dCjD0Lp/IA/ABN4kFesAong3mIiQlQBW5jAjsIpw5hXCrQFxmTirg2ksQ6fxajYN4FbWkE71xoArUgyegsYRMSqdnMTGCuc0Q/6XsLT10DoKyviV4XX9EgMZBKOngKJ0DeK/xJsxDkTukDbS3h6QHXFOGOTaC5Tk3B0q45Ro1Mn4emOQ9y4ryi0luWK3OmRgzOTSGO/yR3dWb2Ezo70FUdfUGC5SeDNKTxfxJWnIg6ai5iwJG/ouqbuDJzEfbxvG99aPpF1/rBFB2j7tvGtFVAdMX4zcpfJ0QEn4S7TWOFlvM6u5xtS6zNTlNUPlHTRgWIjrdBae3aklVpDG2k2VMvXDlUb68PFx7o9LCU5F8y5omh8WCpmzIXKKbmnYq/pafv/kqfNfuMGp2QcyPflGUds1gqMOCFDmIDd45gv7/C0PwI2qFLfvhO0LQAAAABJRU5ErkJggg==") }
    val coScope = rememberCoroutineScope()

    Column {
        Button(
            onClick = {
                val decoded = Base64.decode(base64.value)
                val bitmap = decodeByteArrayToImageBitmap(decoded)
                if (bitmap != null) {
                    bitmapState.value = bitmap
                }
            }
        ) {
            Text("Exibir arquivo")
        }

        bitmapState.value?.let {
            Image(
                bitmap = it,
                contentDescription = "Imagem teste"
            )
        } ?: run {
            Text("Nenhuma imagem selecionada")
        }

        WebViewScreen(url)
    }
}