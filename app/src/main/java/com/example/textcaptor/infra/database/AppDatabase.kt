package com.example.textcaptor.infra.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.textcaptor.app.data.entity.HighlightedTextEntity
import com.example.textcaptor.app.data.repository.dao.HighlightedTextDao


@Database(
    entities = [HighlightedTextEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dao(): HighlightedTextDao
}