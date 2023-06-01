package com.example.textcaptor.app.data.repository.local

import com.example.textcaptor.app.data.entity.HighlightedTextEntity
import com.example.textcaptor.app.data.repository.dao.HighlightedTextDao
import javax.inject.Inject

class HighlightTextRepositoryImpl
    @Inject constructor(private val dao: HighlightedTextDao): HighlightTextRepository {
    override suspend fun saveText(text: HighlightedTextEntity): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getTexts(): List<HighlightedTextEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getTextById(id: String): HighlightedTextEntity {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTextById(id: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateText(text: HighlightedTextEntity): HighlightedTextEntity {
        TODO("Not yet implemented")
    }
}