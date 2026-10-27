package com.capyreader.app.ui.articles.reader

import com.jocmp.mallet.LinearArticle
import com.jocmp.mallet.LinearAudio
import com.jocmp.mallet.LinearBlockQuote
import com.jocmp.mallet.LinearElement
import com.jocmp.mallet.LinearImage
import com.jocmp.mallet.LinearListItem
import com.jocmp.mallet.LinearTable
import com.jocmp.mallet.LinearText
import com.jocmp.mallet.LinearVideo

private const val WORDS_PER_MINUTE = 200
private val WORD = Regex("\\S+")

internal fun estimateReadingTimeMinutes(article: LinearArticle): Int? {
    val wordCount = article.elements.sumOf { it.wordCount() }

    if (wordCount == 0) {
        return null
    }

    return (wordCount + WORDS_PER_MINUTE - 1) / WORDS_PER_MINUTE
}

private fun LinearElement.wordCount(): Int {
    return when (this) {
        is LinearText -> WORD.findAll(text).count()
        is LinearBlockQuote -> content.sumOf { it.wordCount() }
        is LinearListItem -> content.sumOf { it.wordCount() }
        is LinearTable -> cells.values
            .filterNot { it.isFiller }
            .sumOf { cell -> cell.content.sumOf { it.wordCount() } }
        is LinearImage -> caption?.wordCount() ?: 0
        is LinearAudio,
        is LinearVideo,
        -> 0
    }
}
