package com.example.trackerexpenses.appModule

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.trackerexpenses.groceryDb.GroceryDao
import com.example.trackerexpenses.groceryDb.GroceryItem
import com.example.trackerexpenses.incomeDb.IncomeDao
import com.example.trackerexpenses.incomeDb.IncomeItem
import com.example.trackerexpenses.utils.Converters

@Database(entities = [GroceryItem::class, IncomeItem::class], version = 2)
@TypeConverters(Converters::class)
abstract class GroceryDatabase : RoomDatabase() {
    abstract fun groceryDao(): GroceryDao
    abstract fun incomeDao(): IncomeDao

    companion object {
        @Volatile
        private var INSTANCE: GroceryDatabase? = null

        fun getDatabase(context: Context): GroceryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GroceryDatabase::class.java,
                    "grocery_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `income_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `incomeAmount` TEXT NOT NULL)"
        )
    }
}

