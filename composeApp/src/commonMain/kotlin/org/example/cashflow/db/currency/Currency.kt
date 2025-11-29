package org.example.cashflow.db.currency

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CurrencyPound
import androidx.compose.material.icons.filled.CurrencyRuble
import androidx.compose.material.icons.filled.CurrencyYen
import androidx.compose.material.icons.filled.CurrencyYuan
import androidx.compose.material.icons.filled.Euro
import androidx.compose.ui.graphics.vector.ImageVector

enum class Currency(val title: String, val icon: ImageVector) {
    Ruble("RUB", Icons.Default.CurrencyRuble),
    Dollar("USD", Icons.Default.AttachMoney),
    Pound("GBP", Icons.Default.CurrencyPound),
    Euro("EUR", Icons.Default.Euro),
    Yen("JPY", Icons.Default.CurrencyYen),
    Yuan("CNY", Icons.Default.CurrencyYuan)
}