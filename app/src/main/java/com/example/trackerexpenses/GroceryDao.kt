package com.example.trackerexpenses

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.trackerexpenses.incomeDb.IncomeItem


@Dao
interface GroceryDao {
    @Insert
    suspend fun insert(item: GroceryItem)

    @Query("SELECT * FROM grocery_items")
    suspend fun getAllItems(): List<GroceryItem>

    @Delete
    suspend fun delete(item: GroceryItem)
}

@Dao
interface IncomeDao {
    @Insert
    suspend fun insert(income: IncomeItem)

    @Query("SELECT * FROM income_table")
    suspend fun getAllIncome():  List<IncomeItem>
}