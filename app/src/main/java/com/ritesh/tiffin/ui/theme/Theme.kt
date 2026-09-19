package com.ritesh.tiffin.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape

private val TiffinColorScheme = lightColorScheme(
    primary = TiffinTerracotta,
    onPrimary = TiffinSurface,
    primaryContainer = TiffinPeach,
    onPrimaryContainer = TiffinTerracottaDark,
    secondary = TiffinGreen,
    onSecondary = TiffinSurface,
    secondaryContainer = TiffinSoftGreen,
    onSecondaryContainer = TiffinBrown,
    background = TiffinCream,
    surface = TiffinSurface,
    surfaceVariant = TiffinSurfaceMuted,
    onBackground = TiffinBrown,
    onSurface = TiffinBrown,
    onSurfaceVariant = TiffinMutedBrown,
    outline = TiffinOutline,
    outlineVariant = TiffinSurfaceMuted,
)

private val TiffinTypography = Typography(
    displaySmall = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 38.sp,
        lineHeight = 44.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 36.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 30.sp,
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 1.sp,
    ),
)

private val TiffinShapes = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp),
)

@Composable
fun TiffinTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = TiffinColorScheme,
        typography = TiffinTypography,
        shapes = TiffinShapes,
        content = content,
    )
}
