package com.example.trackerexpenses.screen.home.add

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackerexpenses.appModule.GroceryDatabase
import com.example.trackerexpenses.incomeDb.IncomeItem
import kotlinx.coroutines.launch

class IncomeViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = GroceryDatabase.getDatabase(application).incomeDao()

    private val _income = mutableStateOf<List<IncomeItem>>(emptyList())
    val income: State<List<IncomeItem>> get() = _income


    init {
        viewModelScope.launch {
            _income.value = dao.getAllIncome()
        }
    }

    fun addItem(item: IncomeItem) {
        viewModelScope.launch {
            dao.insert(item)
            _income.value += item
        }
    }
}