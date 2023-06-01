package com.example.textcaptor.app.data.repository.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.textcaptor.app.data.entity.HighlightedTextEntity

interface HighlightTextRepository {
    suspend fun saveText(text: HighlightedTextEntity): Boolean

    suspend fun getTexts(): List<HighlightedTextEntity>

    suspend fun getTextById(id: String): HighlightedTextEntity

    suspend fun deleteTextById(id: String): Boolean

    suspend fun updateText(text: HighlightedTextEntity): HighlightedTextEntity
}