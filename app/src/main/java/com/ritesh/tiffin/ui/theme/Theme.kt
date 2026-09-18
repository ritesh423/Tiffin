package com.ritesh.tiffin.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val TiffinColorScheme = lightColorScheme(
    primary = TiffinGreen,
    background = TiffinCream,
    surface = TiffinCream,
    onBackground = TiffinBrown,
    onSurface = TiffinBrown,
)

@Composable
fun TiffinTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = TiffinColorScheme,
        content = content,
    )
}

