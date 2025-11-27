package org.example.cashflow.ui.waste

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import org.example.cashflow.db.WasteCard
import org.example.cashflow.db.WasteItemDB

@Composable
fun WasteCard(wasteCard: WasteCard){
        Column {
            wasteCard.listWaste.forEach { value ->
                WasteItem(
                    WasteItemDB(
                        value.wasteCategory,
                        value.cost,
                        value.currency
                    ),
                    wasteCard.date
                )
            }
        }

}