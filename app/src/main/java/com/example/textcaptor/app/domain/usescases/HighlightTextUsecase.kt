package com.example.textcaptor.app.domain.usescases

import com.example.textcaptor.app.data.entity.HighlightedTextEntity
import com.example.textcaptor.app.domain.model.HighlightedText

interface HighlightTextUsecase {
    suspend fun saveText(text: HighlightedText): Boolean

    suspend fun getTexts(): List<HighlightedText>

    suspend fun getTextById(id: String): HighlightedText?

    suspend fun deleteTextById(id: String): Boolean

    suspend fun updateText(text: HighlightedTextEntity): HighlightedText?
}