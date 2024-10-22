package com.example.recipefinder.core.data.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val title: String,
    val image: String,
    val readyInMinutes: Int,
    val instructions: String
)

