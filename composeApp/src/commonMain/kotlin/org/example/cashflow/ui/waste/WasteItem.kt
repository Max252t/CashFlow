package org.example.cashflow.ui.waste

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import org.example.cashflow.db.convertDB.Converter
import org.example.cashflow.db.currency.Currency
import org.example.cashflow.db.waste.Waste
import org.example.cashflow.db.waste.WasteCategories
import org.example.cashflow.db.waste.WasteItemDB
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.pow
import kotlin.math.round

@Preview(showBackground = true)
@Composable
fun WasteItem(
    waste: Waste,
    onItemRemoved: (Waste) -> Unit = {}
){
    var scaleY by remember { mutableStateOf(Animatable(initialValue = 1f)) }
    var animated by remember { mutableStateOf(false) }

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
                SwipeToDismissBoxValue.StartToEnd,
                SwipeToDismissBoxValue.EndToStart -> {
                    animated = true
                    onItemRemoved(waste)
                }
                SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState true
        },
        positionalThreshold = { it * .25f }
    )

    LaunchedEffect(animated){
        scaleY.animateTo(
            targetValue = if (animated)  0f else 1f,
            animationSpec = tween(durationMillis = 400)
        )
    }
    if (!animated) {
        SwipeToDismissBox(
            state = dismissState,
            modifier = Modifier.graphicsLayer {
               this.scaleY = scaleY.value
            },
            backgroundContent = { DismissBackground(dismissState) }
        ) {
            WasteCore(wasteItem)
        }
    }
}
fun Double.roundTo(dec: Int): Double {
    return round(this * 10.0.pow(dec)) / 10.0.pow(dec)
}


