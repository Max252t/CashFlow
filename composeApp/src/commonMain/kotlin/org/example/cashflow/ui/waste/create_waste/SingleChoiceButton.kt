package org.example.cashflow.ui.waste.create_waste

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.example.cashflow.ui.ColorsUI

@Stable
@Composable
internal fun SingleChoiceButton(
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