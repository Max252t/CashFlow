package org.example.cashflow.ui.waste

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import org.example.cashflow.db.waste.WasteCard
import org.example.cashflow.ui.ColorsUI
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Preview(showBackground = true)
@Composable
fun CreateWaste(
    onDismiss: () -> Unit,
    onCreate: (wasteCard: WasteCard) -> Unit
) {
    val date = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val dateFormat = LocalDate.Format {
        day()
        chars(".")
        monthNumber()
        chars(".")
        year()
    }
    val wasteCard = remember {
        mutableStateOf(WasteCard(
            emptyList(),
            ""))
    }
    Card(
        modifier = Modifier
        .fillMaxWidth(0.7f),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = ColorsUI.backgroundColor
        )
        ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        onDismiss()
                    },

                    ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "close"
                    )
                }
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    IconButton(
                        onClick = {
                            onDismiss()
                            onCreate(wasteCard.value)
                        },
                    ) {
                        Icon(
                            Icons.Default.Done,
                            contentDescription = "done"
                        )
                    }
                }

            }
            SingleChoiceButton(
                onEdit = {
                    OnEditWaste{wasteList ->
                        wasteCard.value = WasteCard(
                            listWaste = wasteList,
                            date = dateFormat.format(date)
                            )
                    }
                },
                byCamera = {},

            )
        }
    }
}



@Stable
@Composable
fun SingleChoiceButton(
    modifier: Modifier = Modifier.fillMaxWidth(),
    onEdit: @Composable () -> Unit,
    byCamera: @Composable () -> Unit,
){
    var selectedIndex by remember { mutableStateOf(0) }
    val options = listOf(Icons.Default.Edit, Icons.Default.Camera) // Defines button icons
    Column {
        SingleChoiceSegmentedButtonRow(modifier) {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = options.size,
                    ), // *Required: Sets button shape based on position
                    onClick = {
                        selectedIndex = index
                    },
                    selected = index == selectedIndex,
                    label = {
                        Icon(label,
                        contentDescription = null)
                            },
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = ColorsUI.dark_cian,
                        inactiveContainerColor = Color.LightGray,
                        inactiveContentColor = Color.White,
                        activeContentColor = Color.Black
                    )
                )
            }
        }
        when(selectedIndex){
            0 -> onEdit()
            1 -> byCamera()
        }
    }
}

expect val myLang:String?
