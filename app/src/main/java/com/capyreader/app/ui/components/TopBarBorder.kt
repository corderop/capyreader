package com.capyreader.app.ui.components

import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp

private val BorderThickness = 0.5.dp

/**
 * Draws a hairline along the bottom edge of a top app bar.
 *
 * The line is drawn inside the bar's bounds instead of below it so the bar keeps its measured
 * height. The reader and the page-turn gestures position content from that height.
 */
@Composable
fun Modifier.topBarBorder(): Modifier {
    val color = colorScheme.outlineVariant

    return drawWithContent {
        drawContent()

        val thickness = BorderThickness.toPx()

        drawLine(
            color = color,
            start = Offset(0f, size.height - thickness / 2),
            end = Offset(size.width, size.height - thickness / 2),
            strokeWidth = thickness,
        )
    }
}
