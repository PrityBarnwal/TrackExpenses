package com.example.trackerexpenses.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trackerexpenses.GroceryViewModel
import java.time.LocalDateTime


@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = viewModel<GroceryViewModel>()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {

        OverAllBalance()
        IncomeExpenditure()

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
        Spacer(modifier = Modifier.height(20.dp))
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
                    .background(Color.Magenta.copy(0.8f), shape = RoundedCornerShape(12.dp))
                    .padding(8.dp), textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        val currentTime = LocalDateTime.now()
        LazyColumn {
            items(viewModel.groceryItems) {item->
                CardHomeRecentTransaction(category = item.name, description = item.note, price ="-${item.price}", time ="$currentTime" )
            }
        }

    }
}

@Composable
fun CardHomeRecentTransaction(category:String,description:String,price: String,time:String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color.Gray, RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(text =category, color = Color.White, fontSize = 12.sp)
            Text(text = description, color = Color.White, fontSize = 10.sp)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(text = price, color = Color.White, fontSize = 12.sp)
            Text(text = time, color = Color.White, fontSize = 10.sp)
        }
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