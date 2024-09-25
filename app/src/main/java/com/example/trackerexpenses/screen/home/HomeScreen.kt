package com.example.trackerexpenses.screen.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {

        OverAllBalance()
        IncomeExpenditure()

        TotalExpense()
        Spacer(modifier = Modifier.height(20.dp))
        ViewAll{
           navController.navigate(RouteApp.TransactionScreen.route)
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {
            items(groceryItems.take(5)) { item ->
                CardHomeRecentTransaction(
                    category = item.name,
                    description = item.note,
                    price = "-${item.price}",
                    time = item.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
            }
        }
    }
}

@Composable
fun CardHomeRecentTransaction(category: String, description: String, price: String, time: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
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

@Composable
fun TotalExpense() {
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
            TotalExpenses("Days", "$123")
            TotalExpenses("Weekly", "$245")
            TotalExpenses("Monthly", "$1234")
            TotalExpenses("Yearly", "$567")

        }
    }
}

@Composable
fun ViewAll(seeAll:()->Unit) {
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
            text = "See All", color = Color.White, fontSize = 14.sp, modifier = Modifier.clickable {
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