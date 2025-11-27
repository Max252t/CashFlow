package org.example.cashflow.ui.waste

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cashflow.composeapp.generated.resources.Res
import cashflow.composeapp.generated.resources.label_money
import org.example.cashflow.db.waste.WasteCategories
import org.example.cashflow.db.waste.WasteItemDB
import org.example.cashflow.db.currency.Currency
import org.example.cashflow.ui.ColorsUI
import org.example.cashflow.viewmodels.HomeScreenComponent
import org.jetbrains.compose.resources.stringResource
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
           EditItem{ cost, currency, wasteCategories ->
               wasteList[index] = WasteItemDB(wasteCategories,
                   cost,
                   currency)
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
                        containerColor = ColorsUI.light_cian
                    )
                ){
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "add_waste"
                        )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun EditItem(onCreateItem: (
    cost: Float,
    currency: Currency,
    wasteCategories: WasteCategories
        ) -> Unit) {

    val wasteCategories = WasteCategories.entries.toTypedArray()
    val currency = Currency.entries.toTypedArray()

    var isMenuCheck by remember { mutableStateOf(false) }
    val rotation = remember { Animatable(initialValue = 1f) }
    var selectWaste by remember { mutableStateOf(WasteCategories.Other) }
    var selectCurrency by remember { if (myLang == "ru") mutableStateOf(Currency.Ruble)
    else mutableStateOf(Currency.Dollar) }
    var wasteInput by remember { mutableStateOf("") }
    var isCurrencyCheck by remember { mutableStateOf(false) }
    onCreateItem(if (wasteInput!="")wasteInput.toFloat() else 0f,
        selectCurrency,
        selectWaste
    )
    LaunchedEffect(isMenuCheck) {
        rotation.animateTo(
            targetValue = if (isMenuCheck) 180f else 1f
        )
    }

    DropdownMenu(
        expanded = isMenuCheck,
        onDismissRequest = {
            isMenuCheck = false
        },
        containerColor = Color(0xFFCCFFF7)
    ) {
        wasteCategories.forEach {
            DropdownMenuItem(
                onClick = {
                    isMenuCheck = false
                    selectWaste = it

                },
                leadingIcon = {
                    Icon(
                        it.icon,
                        contentDescription = it.name.lowercase()
                    )
                },
                text = { Text(stringResource(it.title)) }
            )
        }
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(7.dp),
        verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {
            isMenuCheck = !isMenuCheck
        }){
            Icon(Icons.Default.ArrowDropDown,
                contentDescription = "arrow_down",
                modifier = Modifier.graphicsLayer{
                    rotationX = rotation.value
                }
                )
        }
        Icon(
            selectWaste.icon,
            contentDescription = "select_waste",
            Modifier.padding(2.dp)
        )
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ){
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = ColorsUI.backgroundColor,
                    disabledContainerColor = ColorsUI.light_cian,
                    unfocusedContainerColor = ColorsUI.light_cian,
                    focusedLabelColor = Color.Black,
                    disabledLabelColor = Color.DarkGray,
                    unfocusedLabelColor = Color.DarkGray,
                ),
                value = wasteInput,
                onValueChange = {
                    if (wasteInput.length <= 8 || it.length < 10) {
                        wasteInput = it
                    } },
               trailingIcon = {
                   IconButton(onClick = {
                       isCurrencyCheck = !isCurrencyCheck
                   }) {
                       Icon(
                           selectCurrency.icon,
                           contentDescription = "money",
                           tint = Color.Black
                       )
                   }},
                label = {Text(stringResource(Res.string.label_money))},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
        }
    }
    DropdownMenu(
        expanded = isCurrencyCheck,
        onDismissRequest = {
            isCurrencyCheck = false
        },
        containerColor = Color(0xFFCCFFF7)
    ) {
        currency.forEach {
            DropdownMenuItem(
                onClick = {
                    isCurrencyCheck = false
                    selectCurrency = it
                },
                leadingIcon = {
                    Icon(
                        it.icon,
                        contentDescription = it.name.lowercase()
                    )
                },
                text = { Text(it.name) }
            )
        }
    }
}