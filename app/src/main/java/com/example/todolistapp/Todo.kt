package com.example.todolistapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    val title : String,
    val priority : Int,
    val description : String,
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0
){
    fun doesMatchSearchQuery(query: String): Boolean{
        val matchingCombinations = listOf(
            title
        )

        return matchingCombinations.any{
            it.contains(query, ignoreCase = true)
        }
    }
}
