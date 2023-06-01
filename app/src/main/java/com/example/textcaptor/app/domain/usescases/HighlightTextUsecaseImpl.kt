package com.example.textcaptor.app.domain.usescases

import com.example.textcaptor.app.data.entity.HighlightedTextEntity
import com.example.textcaptor.app.data.repository.local.HighlightTextRepository
import com.example.textcaptor.app.domain.model.HighlightedText
import javax.inject.Inject

class HighlightTextUsecaseImpl
@Inject constructor(private val repository: HighlightTextRepository) : HighlightTextUsecase {
    override suspend fun saveText(text: HighlightedText): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getTexts(): List<HighlightedText> {
        TODO("Not yet implemented")
    }

    override suspend fun getTextById(id: String): HighlightedText {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTextById(id: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateText(text: HighlightedTextEntity): HighlightedText {
        TODO("Not yet implemented")
    }
}