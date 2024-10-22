package com.example.recipefinder.core.data.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "ingredients",
    foreignKeys = [ForeignKey(
        entity = RecipeEntity::class,
        parentColumns = ["id"],
        childColumns = ["recipeId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["recipeId"])]
)
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true) val ingredientId: Int,
    val recipeId: Int,
    val name: String,
    val amount: Double,
    val unit: String,
)

