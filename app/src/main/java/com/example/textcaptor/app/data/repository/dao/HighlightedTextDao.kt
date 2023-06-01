package com.example.textcaptor.app.data.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.textcaptor.app.data.entity.HighlightedTextEntity
import com.example.textcaptor.app.domain.model.HighlightedText

@Dao
interface HighlightedTextDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveText(text: HighlightedTextEntity): Boolean

    @Query("SELECT * FROM highlighted_text")
    suspend fun getTexts(): List<HighlightedTextEntity>

    @Query("SELECT * FROM highlighted_text WHERE id = :id")
    suspend fun getTextById(id: String): HighlightedTextEntity

    @Query("DELETE FROM highlighted_text WHERE id = :id")
    suspend fun deleteTextById(id: String): Boolean

    @Update
    suspend fun updateText(text: HighlightedTextEntity): HighlightedTextEntity
}