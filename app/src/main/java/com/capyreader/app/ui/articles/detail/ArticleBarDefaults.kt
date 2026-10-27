package com.capyreader.app.ui.articles.detail

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.capyreader.app.ui.components.Spacing

object ArticleBarDefaults {
    val TopBarHeight = Spacing.topBarHeight
    val FloatingToolbarHeight = 64.dp
    val FloatingToolbarBottomGap = 12.dp
    val BottomBarHeight = FloatingToolbarHeight + FloatingToolbarBottomGap

    val topBarOffset: Dp
        @Composable get() = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + TopBarHeight
}
