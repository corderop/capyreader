package com.capyreader.app.ui.articles.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.capyreader.app.ui.articles.FaviconBadge
import com.capyreader.app.ui.components.ProvideContentColorTextStyle
import com.capyreader.app.ui.theme.CapyTheme

@Composable
fun ArticleListItem(
    headlineContent: @Composable () -> Unit,
    overlineContent: @Composable() (() -> Unit)? = null,
    supportingContent: @Composable() (() -> Unit)? = null,
    trailingContent: @Composable() (() -> Unit)? = null,
    colors: ListItemColors = ListItemDefaults.colors()
) {
    Surface(
        color = colors.containerColor,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = TopPadding,
                    start = VerticalPadding,
                    end = VerticalSpacing + 4.dp,
                    bottom = BottomPadding,
                )
        ) {
            overlineContent?.let {
                Row {
                    ProvideTextStyleFromToken(
                        colors.overlineContentColor,
                        typography.labelSmall,
                        it,
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(VerticalSpacing)
            ) {
                Column(Modifier.weight(1f)) {
                    ProvideTextStyleFromToken(
                        colors.contentColor,
                        typography.bodyLarge,
                    ) {
                        headlineContent()
                    }

                    supportingContent?.let {
                        ProvideTextStyleFromToken(
                            colors.supportingContentColor,
                            typography.bodyMedium,
                            it,
                        )
                    }
                }
                trailingContent?.let {
                    Column {
                        ProvideTextStyleFromToken(
                            colors.trailingContentColor,
                            typography.labelSmall,
                            it,
                        )
                    }
                }
            }
        }
    }
}

private val VerticalSpacing = 16.dp

private val VerticalPadding = 16.dp

private val TopPadding = 8.dp

private val BottomPadding = 16.dp

@Composable
private fun ProvideTextStyleFromToken(
    color: Color,
    textStyle: TextStyle,
    content: @Composable () -> Unit,
) =
    ProvideContentColorTextStyle(
        contentColor = color,
        textStyle = textStyle,
        content = content
    )

@Preview
@Composable
private fun ArticleListItemPreview() {
    CapyTheme {
        ArticleListItem(
            headlineContent = {
                Text("Headline")
            },
            overlineContent = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    FaviconBadge(url = null)
                    Text("Overline")
                }
            },
            supportingContent = {
                Text("Supporting")
            },
            trailingContent = {
                Text("Trailing")
            },
        )
    }
}
