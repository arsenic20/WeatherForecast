package com.example.taskManager.hilt

import android.content.Context
import androidx.room.Room
import com.example.taskManager.networks.TaskRepository
import com.example.taskManager.roomDb.TaskDao
import com.example.taskManager.roomDb.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): TaskDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            TaskDatabase::class.java,
            "tasks"
        ).build()
    }

    @Provides
    fun provideDao(database: TaskDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    fun provideMyRepository(dao: TaskDao): TaskRepository {
        return TaskRepository(dao)
    }
}