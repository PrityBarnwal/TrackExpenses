package com.example.trackerexpenses.incomeDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IncomeDao {
    @Insert
    suspend fun insert(income: IncomeItem)

    @Query("SELECT * FROM income_table")
    suspend fun getAllIncome():  List<IncomeItem>
}