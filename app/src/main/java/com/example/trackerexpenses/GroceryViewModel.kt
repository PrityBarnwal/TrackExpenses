package com.example.trackerexpenses

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class GroceryViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = GroceryDatabase.getDatabase(application).groceryDao()
    private val _groceryItems = mutableStateListOf<GroceryItem>()
    val groceryItems: List<GroceryItem> get() = _groceryItems
    var searchResults: List<Product> = emptyList()

    init {
        viewModelScope.launch {
            _groceryItems.addAll(dao.getAllItems())
        }
    }

    fun addItem(item: GroceryItem) {
        viewModelScope.launch {
            dao.insert(item)
            _groceryItems.add(item)
        }
    }

    fun deleteItem(item: GroceryItem) {
        viewModelScope.launch {
            dao.delete(item)
            _groceryItems.remove(item)
        }
    }

    fun searchProducts(query: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.searchProducts(query)
                if (response.isSuccessful) {
                    response.body()?.let {
                        searchResults = it.products
                    }
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
