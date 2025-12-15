package org.example.cashflow.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.example.cashflow.ui.donut_chart.DonutChart
import org.example.cashflow.viewmodels.WasteScreenComponent

@Composable
fun WasteScreen(
    component: WasteScreenComponent,
    modifier: Modifier = Modifier.fillMaxWidth()
){
    val wastes by component.wasteState.collectAsState(initial = emptyList())
    val mapWaste = wastes.associateBy { it.listWasteCategories }.mapValues { (_, value) -> value.cost.toDouble().toInt() }
    Column(modifier) {
        DonutChart(
            mapWaste,
        )
        WasteCards(
            wasteCards = wastes,
            isWasteScreen = true,
            modifier = modifier
        )
    }
}