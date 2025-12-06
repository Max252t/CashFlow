package org.example.cashflow.ui.waste

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.cashflow.db.convertDB.Converter
import org.example.cashflow.db.currency.Currency
import org.example.cashflow.db.waste.Waste
import org.example.cashflow.db.waste.WasteCategories
import org.example.cashflow.db.waste.WasteItemDB
import org.example.cashflow.viewmodels.SingletonHome
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.pow
import kotlin.math.round

@Preview(showBackground = true)
@Composable
fun WasteItem(
    waste: Waste
              ){
    val converter = Converter(waste)
    val wasteItem = WasteItemDB(
        converter.getListWaste()[0] ?: WasteCategories.Other,
        converter.getListCost()[0],
        converter.getListCurrency()[0] ?: Currency.Ruble,
        waste.id
        )
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when(it) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    SingletonHome.homeScreenComponent.deleteWaste(waste)
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    SingletonHome.homeScreenComponent.deleteWaste(waste)
                }
                SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState true
        },
        positionalThreshold = { it * .25f }
    )
    SwipeToDismissBox(
        state = dismissState,
        modifier = Modifier,
        backgroundContent = { DismissBackground(dismissState)}
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = wasteItem.wasteCategory.icon,
                    contentDescription = wasteItem.wasteCategory
                        .name
                        .lowercase(),
                    Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color(0xFF0B0822)),
                    tint = Color.White,
                )
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(
                        text = stringResource(wasteItem.wasteCategory.title),
                        fontSize = 19.sp
                    )
                    Text(
                        text = wasteItem.date,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                }
                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${
                                wasteItem
                                    .cost
                                    .toDouble()
                                    .roundTo(2)
                            }",
                            fontSize = 22.sp,
                            modifier = Modifier
                                .padding(3.dp)
                        )
                        Icon(
                            wasteItem.currency.icon,
                            contentDescription = "currency"
                        )
                    }
                }
            }
            HorizontalDivider(color = Color.LightGray)
        }
    }
}
fun Double.roundTo(dec: Int): Double {
    return round(this * 10.0.pow(dec)) / 10.0.pow(dec)
}


