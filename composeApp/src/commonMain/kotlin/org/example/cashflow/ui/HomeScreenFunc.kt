package org.example.cashflow.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cashflow.composeapp.generated.resources.Res
import cashflow.composeapp.generated.resources.categories
import cashflow.composeapp.generated.resources.expenses
import cashflow.composeapp.generated.resources.last_waste
import cashflow.composeapp.generated.resources.see_all
import org.example.cashflow.db.waste.WasteCategories
import org.example.cashflow.db.convertDB.Converter
import org.example.cashflow.ui.waste.CategoryCard
import org.example.cashflow.ui.waste.CreateWaste
import org.example.cashflow.ui.waste.WasteCard
import org.example.cashflow.ui.waste.myLang
import org.example.cashflow.viewmodels.HomeScreenComponent
import org.jetbrains.compose.resources.stringResource
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
@Composable
fun HomeScreen(
    component: HomeScreenComponent,
    modifier: Modifier = Modifier.fillMaxWidth(),
    isCreating: MutableState<Boolean>
){
    val wastes by component.wasteState.collectAsState(initial = emptyList())
    val wasteCards = Converter
        .convertWaste(wastes)
    val wasteCategories = WasteCategories.entries.toTypedArray()
    val currency = component.currencyState.collectAsState().value.valute
    val sum = component.sumWastes(wasteCards,
        currency,
        myLang != "ru"
    )
    Box(modifier = modifier){
        Column {
            Text(stringResource(Res.string.expenses),
                fontSize = 21.sp,
                modifier = Modifier.padding(start = 14.dp)
                )
            Text("${sum}${
                if (myLang == "ru") "₽" 
                else
                "$"}",
                fontSize = 28.sp,
                modifier = Modifier.padding(start = 14.dp),
                fontWeight = FontWeight.Bold)
            Text(stringResource(Res.string.categories),
                fontSize = 21.sp,
                modifier = Modifier.padding(start = 14.dp))
            LazyRow {
                itemsIndexed(wasteCategories){index, value ->
                    CategoryCard(
                        value,
                        ColorsUI.categoryColors[index]
                        )
                }
            }
            Box(contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.fillMaxHeight()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = ColorsUI.cardColor
                    )
                ) {

                    LazyColumn(modifier = Modifier.fillMaxHeight(0.5f)) {
                        item {
                            Row {
                                Text(
                                    stringResource(Res.string.last_waste),
                                    color = Color.DarkGray,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(6.dp)
                                )
                                Box(contentAlignment = Alignment.CenterEnd,
                                    modifier = Modifier.weight(1f)
                                    ){
                                    IconButton(onClick = {}) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.ArrowForward,
                                            contentDescription = "seeAll"
                                        )
                                    }
                                }
                            }
                        }
                        itemsIndexed(
                            wasteCards.subList(
                                0,
                                if (wasteCards.size < 6) wasteCards.size else 6
                            )
                        ) { _, value ->
                            WasteCard(value)
                        }
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

