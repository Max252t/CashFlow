package org.example.cashflow.ui.waste.create_waste

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.cashflow.db.waste.WasteCard

@Stable
@Composable
internal fun TopCreateWaste(
    onDismiss: () -> Unit,
    onCreate: (WasteCard) -> Unit,
    wasteCard: MutableState<WasteCard>
) {
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
}
