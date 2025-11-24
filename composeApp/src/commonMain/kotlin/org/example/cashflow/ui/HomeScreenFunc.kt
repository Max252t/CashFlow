package org.example.cashflow.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.cashflow.db.WasteCategories
import org.example.cashflow.db.convertDB.Converter
import org.example.cashflow.ui.donut_chart.DonutChart
import org.example.cashflow.ui.waste.CreateWaste
import org.example.cashflow.ui.waste.WasteCard
import org.example.cashflow.viewmodels.HomeScreenComponent
import org.jetbrains.compose.resources.stringResource


@Composable
fun HomeScreen(
    component: HomeScreenComponent,
    modifier: Modifier = Modifier.fillMaxWidth(),
    isCreating: MutableState<Boolean>
){

    val wastes by component.getWastes().collectAsState(initial = emptyList())
    val wasteCards = Converter
        .convertWaste(wastes)
    val wasteCategories = WasteCategories.entries.toTypedArray()

    Box(modifier = modifier){

        Column {
            DonutChart(
                data = mapOf(
                    Pair(stringResource(wasteCategories[0].title), 20),
                    Pair(stringResource(wasteCategories[1].title), 70),
                    Pair(stringResource(wasteCategories[2].title), 30),
                    Pair(stringResource(wasteCategories[3].title), 90),
                    Pair(stringResource(wasteCategories[4].title), 70),
                    Pair(stringResource(wasteCategories[5].title), 30),
                )
            )
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(13.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
                ) {
                LazyColumn {
                    itemsIndexed(wasteCards){_, value ->
                        WasteCard(value)
                    }
                }
            }
        }
        if (isCreating.value){
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CreateWaste(
                    onDismiss = {isCreating.value = false},
                    onCreate = {wasteCard ->
                        component.createWaste(wasteCard)
                    }
                )
            }
        }
    }

}

