package com.capyreader.app.ui.articles.reader

import com.jocmp.mallet.LinearArticle
import com.jocmp.mallet.LinearText
import com.jocmp.mallet.LinearTextBlockStyle
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
}
