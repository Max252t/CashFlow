package org.example.cashflow.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cashflow.composeapp.generated.resources.Res
import cashflow.composeapp.generated.resources.last_waste
import org.example.cashflow.db.waste.WasteCard
import org.example.cashflow.navigation.RootComponent
import org.example.cashflow.ui.ColorsUI
import org.example.cashflow.ui.waste.WasteCard
import org.jetbrains.compose.resources.stringResource

@Composable
fun WasteCards(
    wasteCards: List<WasteCard>,
    toWaste: (config: RootComponent.Config) -> Unit = {},
    isWasteScreen: Boolean = false,
    modifier: Modifier = Modifier
               ) {
    Box(
        contentAlignment = if (!isWasteScreen) Alignment.BottomCenter else Alignment.TopCenter,
        modifier = Modifier.fillMaxHeight()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = ColorsUI.cardColor
            )
        ) {

            LazyColumn(modifier = if (!isWasteScreen) Modifier.fillMaxHeight(0.5f) else modifier) {
                item {
                    Row {
                        Text(
                            stringResource(Res.string.last_waste),
                            color = Color.DarkGray,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(6.dp)
                        )
                        if (!isWasteScreen) {
                            Box(
                                contentAlignment = Alignment.CenterEnd,
                                modifier = Modifier.weight(1f)
                            ) {
                                IconButton(onClick = {
                                    toWaste(RootComponent.Config.WasteScreen)
                                }) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = "seeAll"
                                    )
                                }
                            }
                        }
                    }
                }

                itemsIndexed(
                    if (wasteCards.size >6 && !isWasteScreen) wasteCards.takeLast(6) else wasteCards
                ) { _, value ->
                    WasteCard(value)
                }
            }

        }
    }
}