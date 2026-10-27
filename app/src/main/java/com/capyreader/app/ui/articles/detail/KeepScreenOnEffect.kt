package com.capyreader.app.ui.articles.detail

import android.view.WindowManager
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import com.capyreader.app.preferences.AppPreferences
import com.capyreader.app.ui.collectChangesWithCurrent
import org.koin.compose.koinInject

@Composable
fun KeepScreenOnEffect(
    appPreferences: AppPreferences = koinInject(),
) {
    val keepScreenOn by appPreferences.readerOptions.keepScreenOn.collectChangesWithCurrent()
    val window = LocalActivity.current?.window ?: return

    DisposableEffect(window, keepScreenOn) {
        if (keepScreenOn) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

        onDispose {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }
}
