package com.example.todolistapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todolistapp.Todo
import com.example.todolistapp.data.local.dao.TodoDao

@Database(
    entities = [Todo::class],
    version = 1
)

abstract class TodoDatabase:RoomDatabase(){
    abstract val dao: TodoDao
}