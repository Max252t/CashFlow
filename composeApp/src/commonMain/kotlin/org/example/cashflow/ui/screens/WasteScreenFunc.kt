package org.example.cashflow.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.example.cashflow.db.convertDB.Converter
import org.example.cashflow.viewmodels.WasteScreenComponent

@Composable
fun WasteScreen(
    component: WasteScreenComponent,
    modifier: Modifier = Modifier.fillMaxWidth()
){
    val wastes by component.wasteState.collectAsState(initial = emptyList())
    WasteCards(isWasteScreen = true,
        wasteCards = wastes,
        modifier = modifier)
}