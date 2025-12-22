package org.example.cashflow.ui.waste.create_waste

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import org.example.cashflow.db.waste.WasteCard
import org.example.cashflow.ui.ColorsUI
import org.example.cashflow.ui.camera.CameraWaste
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
            TopCreateWaste(onDismiss, onCreate, wasteCard)
            SingleChoiceButton(
                onEdit = {
                    OnEditWaste { wasteList ->
                        wasteCard.value = WasteCard(
                            listWaste = wasteList,
                            date = dateFormat.format(date)
                        )
                    }
                },
                byCamera = {
                    CameraWaste{wasteItemDB ->
                        wasteCard.value = WasteCard(
                            listWaste = listOf(wasteItemDB),
                            date = dateFormat.format(date)
                        )
                    }
                },
            )
        }
    }
}





expect val myLang:String?
