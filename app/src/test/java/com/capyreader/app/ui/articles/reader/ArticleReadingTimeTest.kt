package com.capyreader.app.ui.articles.reader

import com.jocmp.capy.Article
import com.jocmp.mallet.LinearArticle
import com.jocmp.mallet.LinearText
import com.jocmp.mallet.LinearTextBlockStyle
import java.time.ZonedDateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ArticleReadingTimeTest {
    @Test
    fun `empty articles do not have a reading time`() {
        assertNull(estimateReadingTimeMinutes(LinearArticle(elements = emptyList())))
    }

    @Test
    fun `short articles take at least one minute`() {
        val article = articleWithWords(wordCount = 20)

        assertEquals(1, estimateReadingTimeMinutes(article))
    }

    @Test
    fun `reading time rounds up to the next minute`() {
        val article = articleWithWords(wordCount = 401)

        assertEquals(3, estimateReadingTimeMinutes(article))
    }

    @Test
    fun `reading time uses the article content, not the summary`() {
        val article = articleWithContent(wordCount = 1_000, summary = "A short summary.")

        assertEquals(5, estimateReadingTimeMinutes(article))
    }

    @Test
    fun `articles without content do not have a reading time`() {
        assertNull(estimateReadingTimeMinutes(articleWithContent(wordCount = 0)))
    }

    private fun articleWithWords(wordCount: Int): LinearArticle {
        val text = List(wordCount) { "word" }.joinToString(" ")

        return LinearArticle(
            elements = listOf(
                LinearText(
                    ids = emptySet(),
                    text = text,
                    blockStyle = LinearTextBlockStyle.TEXT,
                )
            )
        )
    }

    private fun articleWithContent(wordCount: Int, summary: String = ""): Article {
        val text = List(wordCount) { "word" }.joinToString(" ")
        val now = ZonedDateTime.now()

        return Article(
            id = "288",
            feedID = "123",
            title = "Title",
            author = null,
            contentHTML = "<p>$text</p>",
            url = null,
            summary = summary,
            imageURL = null,
            updatedAt = now,
            publishedAt = now,
            read = false,
            starred = false,
        )
    }
}
