package com.example.trackerexpenses.groceryDb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface GroceryDao {
    @Insert
    suspend fun insert(item: GroceryItem)

    @Query("SELECT * FROM grocery_items")
    suspend fun getAllItems(): List<GroceryItem>

    @Delete
    suspend fun delete(item: GroceryItem)
}