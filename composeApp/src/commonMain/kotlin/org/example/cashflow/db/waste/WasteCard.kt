package org.example.cashflow.db.waste


data class WasteCard(
    val listWaste: List<WasteItemDB>,
    val date: String,
    val card: String = "No_card"
)
