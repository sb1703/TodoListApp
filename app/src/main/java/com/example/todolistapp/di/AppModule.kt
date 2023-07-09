package com.example.todolistapp.di

import android.content.Context
import androidx.room.Room
import com.example.todolistapp.data.local.dao.TodoDao
import com.example.todolistapp.data.local.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(
        @ApplicationContext applicationContext:Context
    ): TodoDatabase {
        val db by lazy {
            Room.databaseBuilder(
                applicationContext,
                TodoDatabase::class.java,
                "todo.db"
            ).build()
        }
        return db
    }

    @Provides
    @Singleton
    fun provideTodoDao(
        todoDatabase: TodoDatabase
    ): TodoDao {
        return todoDatabase.dao
    }
}