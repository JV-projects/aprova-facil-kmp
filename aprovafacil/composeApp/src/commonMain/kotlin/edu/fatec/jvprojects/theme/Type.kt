package edu.fatec.jvprojects.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import aprovafacil.composeapp.generated.resources.LibreBaskerville_Bold
import aprovafacil.composeapp.generated.resources.LibreBaskerville_Regular
import aprovafacil.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun AppFontFamily() = FontFamily(
    Font(Res.font.LibreBaskerville_Bold, FontWeight.Bold),
    Font(Res.font.LibreBaskerville_Regular, FontWeight.Normal)
)

// Default Material 3 typography values
val baseline = Typography()

@Composable
fun AppTypography() = Typography().run {
    val displayFontFamily = AppFontFamily()

    copy(
        displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
        displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
        displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),
        headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily),
        headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily),
        headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily),
        titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily),
        titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily),
        titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily),
    )
}

