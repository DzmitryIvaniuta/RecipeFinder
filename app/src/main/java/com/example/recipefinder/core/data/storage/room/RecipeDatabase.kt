package com.example.recipefinder.core.data.storage.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipefinder.core.data.storage.model.IngredientEntity
import com.example.recipefinder.core.data.storage.model.RecipeEntity

@Database(entities = [RecipeEntity::class, IngredientEntity::class], version = 3, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun getRecipeDao(): RecipeDao

    companion object {
        @Volatile
        private var instance: RecipeDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?:
            createDatabase(context).also{
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RecipeDatabase::class.java,
                "recipe_database"
            ).build()
    }
}