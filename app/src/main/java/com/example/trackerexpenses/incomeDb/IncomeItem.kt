package com.example.trackerexpenses.incomeDb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "income_table")
data class IncomeItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val incomeAmount: String
)