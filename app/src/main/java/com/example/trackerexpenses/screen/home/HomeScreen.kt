package com.example.trackerexpenses.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun HomeScreen(navController: NavController) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)) {
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