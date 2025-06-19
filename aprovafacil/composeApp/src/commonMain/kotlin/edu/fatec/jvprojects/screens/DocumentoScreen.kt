package edu.fatec.jvprojects.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import edu.fatec.jvprojects.composables.WebViewScreen
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