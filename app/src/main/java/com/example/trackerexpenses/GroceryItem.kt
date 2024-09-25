package com.example.trackerexpenses

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "grocery_items")
data class GroceryItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val price: Double,
    val note: String="",
    val time: String="",
    val quantity: Int=0,
)
