package org.example.cashflow.db

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CurrencyPound
import androidx.compose.material.icons.filled.CurrencyRuble
import androidx.compose.material.icons.filled.CurrencyYen
import androidx.compose.material.icons.filled.CurrencyYuan
import androidx.compose.material.icons.filled.Euro
import androidx.compose.ui.graphics.vector.ImageVector

enum class Currency(val icon: ImageVector) {
    Ruble(Icons.Default.CurrencyRuble),
    Dollar(Icons.Default.AttachMoney),
    Pound(Icons.Default.CurrencyPound),
    Euro(Icons.Default.Euro),
    Yen(Icons.Default.CurrencyYen),
    Yuan(Icons.Default.CurrencyYuan)
}