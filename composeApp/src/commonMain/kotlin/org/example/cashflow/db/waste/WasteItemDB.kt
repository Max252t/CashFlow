package org.example.cashflow.db.waste

import org.example.cashflow.db.currency.Currency


data class WasteItemDB(
    val wasteCategory: WasteCategories,
    val cost: Float,
    val currency: Currency,
    val id: Int? = null,
    val date: String = ""
)
