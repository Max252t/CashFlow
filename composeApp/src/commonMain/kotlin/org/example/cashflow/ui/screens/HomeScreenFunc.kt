package org.example.cashflow.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cashflow.composeapp.generated.resources.Res
import cashflow.composeapp.generated.resources.categories
import cashflow.composeapp.generated.resources.expenses
import org.example.cashflow.db.convertDB.Converter
import org.example.cashflow.db.waste.WasteCategories
import org.example.cashflow.navigation.RootComponent
import org.example.cashflow.ui.ColorsUI
import org.example.cashflow.ui.waste.CategoryCard
import org.example.cashflow.ui.waste.create_waste.CreateWaste
import org.example.cashflow.ui.waste.create_waste.myLang
import org.example.cashflow.viewmodels.HomeScreenComponent
import org.jetbrains.compose.resources.stringResource
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
@Composable
fun HomeScreen(
    component: HomeScreenComponent,
    modifier: Modifier = Modifier.fillMaxWidth(),
    isCreating: MutableState<Boolean>,
    rootComponent: RootComponent
){

    val wastes = component.wasteState.collectAsState(emptyList())
    val wasteCards = Converter
        .convertWaste(wastes.value)
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
            WasteCards(
                wasteCards = wastes.value,
                toWaste = rootComponent::navigateTo,
                component = component
                )
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



