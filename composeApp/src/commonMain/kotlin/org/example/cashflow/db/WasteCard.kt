package org.example.cashflow.db


import kotlinx.serialization.Serializable
import org.example.cashflow.db.Currency

data class WasteCard(
    val listWaste: List<WasteItemDB>,
    val date: String
)
