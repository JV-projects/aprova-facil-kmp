package edu.fatec.jvprojects.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState

@Composable
fun WebViewScreen(
    url: String,

) {
    val webViewState = rememberWebViewState(url)

    println("webViewUrl: $url")

    val text = webViewState.let {
        "${it.pageTitle ?: ""} ${it.loadingState} ${it.lastLoadedUrl ?: ""}"
    }
    Text(text)

    if (url.length <= 8) {
        Text("Caminho do arquivo não encontrado")
    } else {
        WebView(
            state = webViewState,
            modifier = Modifier.fillMaxSize(),
        )
    }
}