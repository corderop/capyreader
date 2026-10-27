package com.capyreader.app.ui.articles

import androidx.collection.LruCache
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.capyreader.app.ui.articles.reader.estimateReadingTimeMinutes
import com.jocmp.capy.Account
import com.jocmp.capy.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Reading time for an article in the list, estimated the same way as the reader screen.
 *
 * List articles are loaded without their HTML, so the full article is fetched from the
 * database, then flattened off the main thread. Results are cached by article.
 */
@Composable
fun rememberReadingTimeMinutes(article: Article, account: Account): Int? {
    var minutes by remember(article.id) {
        mutableStateOf(ReadingTimeCache.peek(article.id))
    }

    LaunchedEffect(article.id) {
        minutes = ReadingTimeCache.getMinutes(article.id, account)
    }

    return minutes
}

private object ReadingTimeCache {
    private val cache = LruCache<String, Int>(MAX_ENTRIES)

    /** Cached estimate, or null when there's nothing cached or the article has no content. */
    fun peek(articleID: String): Int? = cache[articleID].takeUnlessUnknown()

    suspend fun getMinutes(articleID: String, account: Account): Int? {
        cache[articleID]?.let { return it.takeUnlessUnknown() }

        val minutes = withContext(Dispatchers.Default) {
            val article = account.findArticle(articleID = articleID)

            article?.let { estimateReadingTimeMinutes(it) } ?: NO_READING_TIME
        }

        cache.put(articleID, minutes)

        return minutes.takeUnlessUnknown()
    }

    private fun Int?.takeUnlessUnknown() = takeUnless { it == null || it == NO_READING_TIME }

    private const val NO_READING_TIME = 0
    private const val MAX_ENTRIES = 500
}
