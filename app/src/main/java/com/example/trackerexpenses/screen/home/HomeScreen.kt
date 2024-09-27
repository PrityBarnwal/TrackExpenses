package com.example.trackerexpenses.screen.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trackerexpenses.GroceryItem
import com.example.trackerexpenses.GroceryViewModel
import com.example.trackerexpenses.navigation.RouteApp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = viewModel<GroceryViewModel>()
    val groceryItems by viewModel.groceryItems

    BackHandler {
        (navController.context as? Activity)?.finish()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {

        OverAllBalance()
        IncomeExpenditure()

        val totalExpenses = calculateTotalExpenses(groceryItems)

        TotalExpense(
            daily = totalExpenses["daily"] ?: "$0",
            weekly = totalExpenses["weekly"] ?: "$0",
            monthly = totalExpenses["monthly"] ?: "$0",
            yearly = totalExpenses["yearly"] ?: "$0"
        )
        Spacer(modifier = Modifier.height(20.dp))
        ViewAll {
            navController.navigate(RouteApp.TransactionScreen.route)
        }

        Spacer(modifier = Modifier.height(10.dp))
        val todayData =
            groceryItems.filter { item -> item.date.toLocalDate() == currentTime.toLocalDate() }
        if (todayData.isNotEmpty()) {
            Text(text = "Today", color = Color.Red, fontSize = 12.sp)
        }

        LazyColumn {
            items(todayData.take(5)) { item ->
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
        val yesterdayData = groceryItems.filter { item ->
            item.date.toLocalDate() == currentTime.minusDays(1).toLocalDate()
        }

        if (yesterdayData.isNotEmpty()) {
            Text(text = "Yesterday", color = Color.Red, fontSize = 12.sp)
        }

        LazyColumn {
            items(yesterdayData.take(5)) { item ->
                CardHomeRecentTransaction(
                    category = item.name,
                    description = item.note,
                    price = "${item.price}",
                    time = item.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                ) {
                    viewModel.deleteItem(item)
                }
            }
        }

    }
}


@Composable
fun CardHomeRecentTransaction(
    category: String,
    description: String,
    price: String,
    time: String,
    onDelete: () -> Unit = {}
) {
    var offsetX by remember { mutableStateOf(0f) }
    var isSwiped by remember { mutableStateOf(false) }

    val dragModifier = Modifier
        .pointerInput(Unit) {
            detectHorizontalDragGestures { change, dragAmount ->
                offsetX = (offsetX + dragAmount).coerceIn(-100f, 0f) // Limit swipe to -100f
                change.consume()
                isSwiped = offsetX < -50f // Update swipe state based on offset
            }
        }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        if (isSwiped) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete",
                tint = Color.White,
                modifier = Modifier
                    .clickable { onDelete.invoke() }
                    .size(30.dp)
                    .align(Alignment.CenterEnd)
                    .padding(end = 12.dp)
            )
        }

        Row(
            modifier = dragModifier
                .fillMaxWidth()
                .graphicsLayer(translationX = offsetX)
                .background(Color.Gray, RoundedCornerShape(12.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(text = category, color = Color.White, fontSize = 12.sp)
                Text(text = description, color = Color.White, fontSize = 10.sp)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(text = price, color = Color.White, fontSize = 12.sp)
                Text(text = time, color = Color.White, fontSize = 10.sp)
            }
        }
    }
}

@Composable
fun TotalExpense(daily: String, weekly: String, monthly: String, yearly: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray, RoundedCornerShape(20.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Text(
            text = "Total Expenditure",
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TotalExpenses("Days", daily)
            TotalExpenses("Weekly", weekly)
            TotalExpenses("Monthly", monthly)
            TotalExpenses("Yearly", yearly)

        }
    }
}

fun calculateTotalExpenses(expenses: List<GroceryItem>): Map<String, String> {
    val today = currentTime.toLocalDate()
    val daily = expenses.filter {
        it.date.toLocalDate() == currentTime.toLocalDate()
    }.sumOf { it.price }

    val weekly = expenses.filter {
        it.date.toLocalDate().isAfter(today.minusDays(7)) && it.date.toLocalDate() <= today
    }.sumOf { it.price }

    val monthly = expenses.filter {
        it.date.toLocalDate().month == currentTime.month &&
                it.date.toLocalDate().year == currentTime.year
    }.sumOf { it.price }

    val yearly = expenses.filter {
        it.date.toLocalDate().year == currentTime.year
    }.sumOf { it.price }

    return mapOf(
        "daily" to "$${daily}",
        "weekly" to "$${weekly}",
        "monthly" to "$${monthly}",
        "yearly" to "$${yearly}"
    )
}


@Composable
fun ViewAll(seeAll: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Recent Transaction",
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.Start
        )
        Text(
            text = "See All", color = Color.White, fontSize = 14.sp, modifier = Modifier
                .clickable {
                    seeAll.invoke()
                }
                .background(Color.Magenta.copy(0.8f), shape = RoundedCornerShape(12.dp))
                .padding(8.dp), textAlign = TextAlign.End
        )
    }
}

@Composable
fun OverAllBalance() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .background(Color.Gray)
    ) {
        Text(
            buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Balance : ")
                }
                append("1234")
            },
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
        )
    }
}

@Composable
fun TotalExpenses(heading: String, price: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = heading,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier
                .background(Color.Blue, shape = RoundedCornerShape(12.dp))
                .padding(8.dp)

        )
        Text(
            text = price,
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun IncomeExpenditure() {
    Column(modifier = Modifier.padding(vertical = 20.dp)) {
        HorizontalDivider(
            thickness = 2.dp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 40.dp)
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Investment", color = Color.White, fontSize = 12.sp)
            Spacer(modifier = Modifier.width(8.dp))
            VerticalDivider(
                thickness = 2.dp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .height(8.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Income", color = Color.White, fontSize = 12.sp)
        }
        HorizontalDivider(
            thickness = 2.dp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 40.dp)
        )
    }

}

val currentTime = LocalDateTime.now()