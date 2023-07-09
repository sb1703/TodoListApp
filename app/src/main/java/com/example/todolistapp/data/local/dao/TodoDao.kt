package com.example.todolistapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.todolistapp.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Upsert
    suspend fun upsertTodo(todo : Todo)

    @Delete
    suspend fun deleteTodo(todo : Todo)

    @Query("SELECT * FROM todo ORDER BY priority ASC")
    fun getTodoOrderedByLowPriority(): Flow<List<Todo>>

    @Query("SELECT * FROM todo ORDER BY priority DESC")
    fun getTodoOrderedByHighPriority(): Flow<List<Todo>>

    @Query("SELECT * FROM todo")
    fun getTodoOrderedByNone(): Flow<List<Todo>>

}