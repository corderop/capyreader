package com.capyreader.app.ui.articles.reader

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capyreader.app.R
import com.capyreader.app.ui.LocalTimeFormats
import com.capyreader.app.ui.articles.FaviconBadge
import com.capyreader.app.ui.articles.displayFeedName
import com.jocmp.capy.Article
import com.jocmp.capy.common.toDeviceDateTime

private val metadataTextStyle = TextStyle(
    fontSize = 14.sp,
    lineHeight = 20.sp,
)

@Composable
fun ArticleHeader(
    article: Article,
    onOpenLink: () -> Unit,
    modifier: Modifier = Modifier,
    readingTimeMinutes: Int? = null,
) {
    val context = LocalContext.current
    val formats = LocalTimeFormats.current
    val readerStyle = LocalReaderStyle.current
    val feedName = article.displayFeedName(context)
    val showPlaceholderTitle = article.title.isBlank()
    val title = if (showPlaceholderTitle) {
        feedName
    } else {
        article.title
    }
    val date = formats.longDate.format(article.publishedAt.toDeviceDateTime())
    val displayName = article.author?.takeUnless { it.isBlank() } ?: feedName

    Column(modifier = modifier.fillMaxWidth()) {
        BidiLayoutDirection(paragraph = title) {
            Text(
                text = title,
                style = readerStyle.titleTextStyle.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { heading() }
                    .clickable(
                        onClickLabel = stringResource(R.string.reader_open_in_browser),
                        onClick = onOpenLink,
                    ),
            )
        }

        Spacer(Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            FaviconBadge(url = article.faviconURL)

            Spacer(Modifier.width(8.dp))

            Text(
                text = displayName,
                style = metadataTextStyle,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = date,
                style = metadataTextStyle,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            readingTimeMinutes?.let { minutes ->
                Spacer(Modifier.width(8.dp))

                Text(
                    text = "•",
                    style = metadataTextStyle,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    text = stringResource(R.string.reader_reading_time, minutes),
                    style = metadataTextStyle,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                )
            }
        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp)
                .width(64.dp),
        )
    }
}
