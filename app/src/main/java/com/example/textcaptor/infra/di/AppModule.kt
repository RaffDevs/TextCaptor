package com.example.textcaptor.infra.di

import android.content.Context
import androidx.room.Room
import com.example.textcaptor.app.data.repository.dao.HighlightedTextDao
import com.example.textcaptor.app.data.repository.local.HighlightTextRepository
import com.example.textcaptor.app.data.repository.local.HighlightTextRepositoryImpl
import com.example.textcaptor.infra.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideTextHighlightDatabase(
        @ApplicationContext
        context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideRepository(dao: HighlightedTextDao): HighlightTextRepository {
        return HighlightTextRepositoryImpl(dao)
    }
}