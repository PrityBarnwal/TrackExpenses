package com.example.trackerexpenses.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
    var selectedOption by remember { mutableStateOf("Monthly") }
    var search by remember { mutableStateOf("") }
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

        CommonOutlinedTextField(
            value = search,
            onValueChange = { search = it },
            modifier = Modifier
                .fillMaxWidth(),
            trailingIcon = if (search.isEmpty()) Icons.Default.Search else Icons.Default.Clear,
            labelText = "Search", trailingIconClickable = {
                search=""
            }
        )

        Spacer(modifier = Modifier.height(10.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            CommonOutlinedTextField(
                value = selectedOption,
                onValueChange = { selectedOption = it },
                modifier = Modifier.width(150.dp),
                trailingIcon = Icons.Default.ArrowDropDown, labelVisible = false,
                readOnly = true
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
        val filteredItems = groceryItems
            .filter { item ->
                when (selectedOption) {
                    "Today" -> item.date.toLocalDate() == currentTime.toLocalDate()
                    "Yesterday" -> item.date.toLocalDate() == currentTime.minusDays(1).toLocalDate()
                    "Weekly" -> item.date.isAfter(currentTime.minusWeeks(1))
                    "Monthly" -> item.date.month == currentTime.month && item.date.year == currentTime.year
                    "Yearly" -> item.date.year == currentTime.year
                    else -> true // If no filter is selected, include all items
                }
            }
            .filter { item ->
                search.isEmpty() || item.name.contains(
                    search,
                    ignoreCase = true
                ) || item.note.contains(search, ignoreCase = true)
            }

        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn {
            items(filteredItems) { item ->
                CardHomeRecentTransaction(
                    category = item.name,
                    description = item.note,
                    price = "-${item.price}",
                    time = item.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                ) {
                    viewModel.deleteItem(item)
                }
            }
        }
    }
}

@Composable
fun CommonOutlinedTextField(
    value: String, // Input value for the TextField
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    trailingIcon: ImageVector,
    labelText: String = "",
    labelVisible: Boolean = true,
    readOnly: Boolean = false,
    trailingIconClickable:()->Unit ={}
) {
    OutlinedTextField(
        readOnly = readOnly,
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        trailingIcon = {
            Icon(
                trailingIcon,
                contentDescription = null,
                tint = Color.White, modifier = Modifier.clickable {
                    trailingIconClickable.invoke()
                }
            )
        },
        singleLine = true,
        label = if (labelVisible) {
            { Text(text = labelText, color = Color.White) }
        } else null,
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 12.sp
        ),
        modifier = modifier,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            cursorColor = Color.White,
            textColor = Color.White,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White
        )
    )
}