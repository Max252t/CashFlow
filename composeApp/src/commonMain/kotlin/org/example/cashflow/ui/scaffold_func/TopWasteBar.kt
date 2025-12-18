package org.example.cashflow.ui.scaffold_func

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Stable
@Composable
fun TopWasteBar() {
    Text(
        "CashFlow",
        fontSize = 23.sp,
        modifier = Modifier
            .systemBarsPadding()
            .padding(start = 6.dp),
        fontWeight = FontWeight.Bold
    )
}
