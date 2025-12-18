package org.example.cashflow.ui.waste.create_waste

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import org.example.cashflow.db.currency.Currency
import org.example.cashflow.db.waste.WasteCategories
import org.example.cashflow.db.waste.WasteItemDB
import org.example.cashflow.ui.ColorsUI
import org.example.cashflow.viewmodels.HomeScreenComponent
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview(showBackground = true)
@Composable
fun OnEditWaste(returnList: (wasteList: List<WasteItemDB>)-> Unit){

    val wasteList = remember {
        mutableStateListOf(WasteItemDB(
        WasteCategories.Other,
        0f,
        Currency.Ruble
    )) }
    LazyColumn {
       itemsIndexed(wasteList){ index, _ ->
           EditItem { cost, currency, wasteCategories ->
               wasteList[index] = WasteItemDB(
                   wasteCategories,
                   cost,
                   currency
               )
               returnList(wasteList)
               HomeScreenComponent.updateWasteCard(wasteList)
           }
       }
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ){
                IconButton(
                    onClick = {
                        HomeScreenComponent.updateWasteCard(wasteList)
                        returnList(wasteList)
                        wasteList.add(WasteItemDB(
                            WasteCategories.Other,
                            0f,
                            Currency.Ruble
                            ))
                    },
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RectangleShape),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = ColorsUI.purple_light
                    )
                ){
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "add_waste",
                        tint = Color.White
                        )
                }
            }
        }
    }
}
