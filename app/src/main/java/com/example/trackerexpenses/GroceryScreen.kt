package com.example.trackerexpenses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.trackerexpenses.screen.home.currentTime


@Composable
fun GroceryScreen(viewModel: GroceryViewModel) {
    var itemName by remember { mutableStateOf("") }
    var itemPrice by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Grocery List", style = MaterialTheme.typography.bodyLarge)

        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.searchProducts(it)
            },
            label = { Text("Search Products") }
        )

//        LazyColumn {
//            items(viewModel.searchResults) { product ->
//                Text("${product.product_name ?: "Unknown Product"}")
//                // Optionally display more product info
//            }
//        }

        TextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text("Item Name") }
        )

        TextField(
            value = itemPrice,
            onValueChange = { itemPrice = it },
            label = { Text("Item Price") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        TextField(
            value = itemQuantity,
            onValueChange = { itemQuantity = it },
            label = { Text("Quantity") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        Button(onClick = {
            val price = itemPrice.toDoubleOrNull() ?: 0.0
            val quantity = itemQuantity.toIntOrNull() ?: 1
            val newItem = GroceryItem(name = itemName, price = price, quantity = quantity, date = currentTime)
            viewModel.addItem(newItem)
            itemName = ""
            itemPrice = ""
            itemQuantity = ""
        }) {
            Text("Add Item")
        }

//        LazyColumn {
//            items(viewModel.groceryItems) { item ->
//                Row {
//                    Text("${item.name} - $${item.price} x${item.quantity}")
//                    Spacer(modifier = Modifier.weight(1f))
//                    IconButton(onClick = { viewModel.deleteItem(item) }) {
//                        Icon(Icons.Filled.Delete, contentDescription = "Delete Item")
//                    }
//                }
//            }
//        }
    }
}
