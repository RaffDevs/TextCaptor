package com.example.textcaptor.app.data.repository.local

import android.util.Log
import com.example.textcaptor.app.data.entity.HighlightedTextEntity
import com.example.textcaptor.app.data.repository.dao.HighlightedTextDao
import javax.inject.Inject

class HighlightTextRepositoryImpl
    @Inject constructor(private val dao: HighlightedTextDao): HighlightTextRepository {
    override suspend fun saveText(text: HighlightedTextEntity): Boolean {
        return try {
            dao.saveText(text)
            true
        } catch (error: Error) {
            Log.d("RepositoryError", "saveText: $error")
            false
        }

    }

    override suspend fun getTexts(): List<HighlightedTextEntity> {
        var texts = listOf<HighlightedTextEntity>()
        try {
            texts = dao.getTexts()
        } catch (error: Error) {
            Log.d("RepositoryError", "saveText: $error")
        }

        return texts

    }

    override suspend fun getTextById(id: String): HighlightedTextEntity? {
        var text: HighlightedTextEntity? = null
        try {
            text = dao.getTextById(id)
        } catch (error: Error) {
            Log.d("RepositoryError", "saveText: $error")
        }

        return text
    }

    override suspend fun deleteTextById(id: String): Boolean {
        return try {
            dao.deleteTextById(id)
            true
        } catch (error: Error) {
            Log.d("RepositoryError", "saveText: $error")
            false
        }
    }

    override suspend fun updateText(text: HighlightedTextEntity): HighlightedTextEntity? {
        var updatedText: HighlightedTextEntity? = null
        try {
            updatedText = dao.updateText(text)
        } catch (error: Error) {
            Log.d("RepositoryError", "saveText: $error")
        }

        return updatedText
    }
}