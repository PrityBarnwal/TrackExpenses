package com.example.trackerexpenses.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trackerexpenses.GroceryViewModel
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TransactionScreen(navController: NavController) {
    val viewModel = viewModel<GroceryViewModel>()
    val groceryItems by viewModel.groceryItems

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Today") }
    val options = listOf("Today", "Yesterday", "Weekly", "Monthly", "Yearly")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Transaction",
            style = MaterialTheme.typography.subtitle1,
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        // Dropdown menu for selecting time periods
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = selectedOption,
                onValueChange = {
                    selectedOption = it
                },
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.White) },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 12.sp
                ),
                modifier = Modifier
                    .width(150.dp)
                    .padding(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.White,
                    textColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOption = option
                            expanded = false
                        }
                    ) {
                        Text(text = option, color = Color.Black)
                    }
                }
            }
        }

        // You can filter grocery items based on selectedOption here
        val filteredItems = when (selectedOption) {
            "Today" -> groceryItems.filter { item -> item.date.toLocalDate() == currentTime.toLocalDate() }
            "Yesterday" -> groceryItems.filter { item ->
                item.date.toLocalDate() == currentTime.minusDays(
                    1
                ).toLocalDate()
            }

            "Weekly" -> groceryItems.filter { item ->
                item.date.isAfter(currentTime.minusWeeks(1))
            }

            "Monthly" -> groceryItems.filter { item ->
                item.date.month == currentTime.month && item.date.year == currentTime.year
            }

            "Yearly" -> groceryItems.filter { item ->
                item.date.year == currentTime.year
            }

            else -> groceryItems
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn {
            items(filteredItems) { item ->
                CardHomeRecentTransaction(
                    category = item.name,
                    description = item.note,
                    price = "-${item.price}",
                    time = item.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                ){
                    viewModel.deleteItem(item)
                }
            }
        }
    }
}

