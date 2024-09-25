package com.example.trackerexpenses.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trackerexpenses.GroceryItem
import com.example.trackerexpenses.GroceryViewModel
import com.example.trackerexpenses.navigation.RouteApp
import java.time.LocalDateTime


@Composable
fun AddScreen(navController: NavController) {
    val viewModel = viewModel<GroceryViewModel>()

    var itemName by remember { mutableStateOf("") }
    var itemPrice by remember { mutableStateOf("") }
    var itemNote by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Expenses", color = Color.White, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text("Item Name", color = Color.White) }, singleLine = true,
            textStyle = TextStyle(color = Color.White, fontSize = 12.sp),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = itemPrice,
            onValueChange = { itemPrice = it },
            label = { Text("Item Price", color = Color.White) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ), singleLine = true,
            textStyle = TextStyle(color = Color.White, fontSize = 12.sp),
            modifier = Modifier.fillMaxWidth()
        )


        OutlinedTextField(
            value = itemNote,
            onValueChange = { itemNote = it },
            label = { Text("Description", color = Color.White) }, singleLine = true,
            textStyle = TextStyle(color = Color.White, fontSize = 12.sp),
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            val price = itemPrice.toDoubleOrNull() ?: 0.0
            val newItem = GroceryItem(name = itemName, price = price,note=itemNote, time ="")
            viewModel.addItem(newItem)
            itemName = ""
            itemPrice = ""
            itemNote = ""
            navController.popBackStack()
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Add Item")
        }

//        LazyColumn {
//            items(viewModel.groceryItems) { item ->
//                CardHomeRecentTransaction(category = item.name, description = item.note, price ="${item.price}", time ="$currentTime" )
//
//            }
//        }
    }
}

//                Row {
//                    Text("${item.name} - $${item.price} x${item.quantity}", color = Color.White)
//                    Spacer(modifier = Modifier.weight(1f))
//                    IconButton(onClick = { viewModel.deleteItem(item) }) {
//                        Box(modifier = Modifier
//                            .background(Color.White)
//                            .clip(CircleShape)) {
//                            Icon(
//                                Icons.Filled.Delete,
//                                contentDescription = "Delete Item",
//                                modifier = Modifier
//                                    .padding(4.dp)
//                            )
//                        }
//                    }
//                }