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

    private val wasteCategories = WasteCategories.entries.toTypedArray().associateBy { it.name}


    @Test
    fun getListWaste(){
       assertEquals(expected = listOf(WasteCategories.Shop,
            WasteCategories.Personal,
            WasteCategories.Other,
            WasteCategories.Travel
            ),
            actual = waste
                .listWasteCategories
                .split("#")
                .map { wasteCategories[it] })

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




    @Test
    fun convertCost(){
        val listCost  = listOf(35.6, 73.8, 23.0)
        var convertedList = listCost.first().toString()
        if (listCost.size>1) {
            listCost.subList(1, listCost.size).forEach {
                convertedList += "#$it"
            }
        }
        assertEquals(expected = "35.6#73.8#23.0", actual = convertedList)
    }

}