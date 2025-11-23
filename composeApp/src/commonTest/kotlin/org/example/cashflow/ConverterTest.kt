package org.example.cashflow

import org.example.cashflow.db.Currency
import org.example.cashflow.db.Waste
import org.example.cashflow.db.WasteCategories
import kotlin.test.Test
import kotlin.test.assertEquals

class ConverterTest {
    val waste = Waste(cost = "34.6#63.8#987#62.6",
        listWasteCategories = "Shop#Personal#Other#Travel",
        id = 7,
        currency = "Ruble#Euro#Dollar#Yen",
        date = "23.11.2025"
        )
    private val wasteCurrency = Currency.entries.toTypedArray().associateBy { it.name}



    @Test
    fun getListWaste(){
       /* assertEquals(expected = listOf<WasteCategories?>(WasteCategories.Shop,
            WasteCategories.Personal,
            WasteCategories.Other,
            WasteCategories.Travel
            ),
            actual = waste
                .listWasteCategories
                .split("#")
                .map { wasteCategories[it] })*/
        assertEquals(expected = 5, actual = 2+3)

    }
    @Test
    fun getListCurrency(){
        assertEquals(actual = waste
            .currency.split("#")
            .map { wasteCurrency[it] },
            expected = listOf(Currency.Ruble,
                Currency.Euro,
                Currency.Dollar,
                Currency.Yen
            ))
    }
    @Test
    fun getListCost(){
        assertEquals(expected = listOf(
            34.6f, 63.8f, 987.0f, 62.6f
        ),
            actual = waste
                .cost.split("#")
                .map { it.toFloat() })
    }


}