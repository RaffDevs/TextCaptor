package com.example.textcaptor.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.textcaptor.app.domain.model.HighlightedText
import java.util.UUID


@Entity(tableName = "highlighted_text")
data class HighlightedTextEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "text")
    val text: String
) {
    companion object {
        fun HighlightedTextEntity.toHighlightedText(): HighlightedText {
            return HighlightedText(
                id = id,
                title = title,
                text = text
            )
        }
    }
}
