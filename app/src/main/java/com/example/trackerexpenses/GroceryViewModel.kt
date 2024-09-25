package com.example.trackerexpenses

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class GroceryViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = GroceryDatabase.getDatabase(application).groceryDao()

    private val _groceryItems = mutableStateOf<List<GroceryItem>>(emptyList())
    val groceryItems: State<List<GroceryItem>> get() = _groceryItems

    var searchResults: List<Product> = emptyList()

    init {
        viewModelScope.launch {
            _groceryItems.value = dao.getAllItems()
        }
    }

    fun addItem(item: GroceryItem) {
        viewModelScope.launch {
            dao.insert(item)
            _groceryItems.value += item
        }
    }

    fun deleteItem(item: GroceryItem) {
        viewModelScope.launch {
            dao.delete(item)
//            _groceryItems.value = _groceryItems.value.filter { it != item }
            _groceryItems.value = _groceryItems.value.filter { it.id != item.id }
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
