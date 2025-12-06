package org.example.cashflow.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.cashflow.viewmodels.AccountScreenComponent

@Composable
fun AccountScreen(
    component: AccountScreenComponent,
    modifier: Modifier = Modifier
                  ){
    Column {
        Row(modifier = modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically) {

        Text("Maxim",
                fontSize = 24.sp,
                fontWeight = FontWeight.W700
                )
        }
    }
}