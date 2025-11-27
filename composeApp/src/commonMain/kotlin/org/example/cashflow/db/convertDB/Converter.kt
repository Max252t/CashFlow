package org.example.cashflow.db.convertDB

import androidx.room.TypeConverter
import org.example.cashflow.db.WasteCategories
import org.example.cashflow.db.Waste
import org.example.cashflow.db.WasteCard
import org.example.cashflow.db.WasteItemDB
import org.example.cashflow.ui.waste.Currency

class Converter(private val waste: Waste) {
    private val wasteCategories = WasteCategories.entries.toTypedArray().associateBy { it.name}
    private val wasteCurrency = Currency.entries.toTypedArray().associateBy { it.name}

    fun getListWaste(): List<WasteCategories?>{
        return waste
            .listWasteCategories
            .split("#")
            .map { wasteCategories[it] }
    }
    fun getListCurrency(): List<Currency?>{
        return waste
            .currency.split("#")
            .map { wasteCurrency[it] }
    }
    fun getListCost(): List<Float>{
        return waste
            .cost.split("#")
            .map { it.toFloat() }
    }
    companion object{
        @TypeConverter
        fun convertWaste(listWaste: List<WasteCategories>): String{
            var convertedList = listWaste.first().name
            if (listWaste.size>1) {
                listWaste.subList(1, listWaste.size).forEach {
                    convertedList += "#" + it.name
                }
            }
            return convertedList
        }
        @TypeConverter
        fun convertCurrency(listCurrency: List<Currency>): String{
            var convertedList = listCurrency.first().name
            if (listCurrency.size>1) {
                listCurrency.subList(1, listCurrency.size).forEach {
                    convertedList += "#" + it.name
                }
            }
            return convertedList
        }
        @TypeConverter
        fun convertCost(listCost: List<Float>): String{
            var convertedList = listCost.first().toString()
            if (listCost.size>1) {
                listCost.subList(1, listCost.size).forEach {
                    convertedList += "#$it"
                }
            }
            return convertedList
        }
        @TypeConverter
        fun convertWasteCard(wasteCard: WasteCard): Waste{
            val costList = mutableListOf<Float>()
            val currencyList = mutableListOf<Currency>()
            val categoryList = mutableListOf<WasteCategories>()
            wasteCard.listWaste.forEach {(wasteCategory, cost, currency) ->
                costList.add(cost)
                currencyList.add(currency)
                categoryList.add(wasteCategory)
            }
            return Waste(
                cost =  convertCost(costList),
                listWasteCategories = convertWaste(categoryList),
                currency = convertCurrency(currencyList),
                date = wasteCard.date
                )
        }
        @TypeConverter
        fun convertWaste(wasteList: List<Waste>): List<WasteCard>{
            val wasteCardList = mutableListOf<WasteCard>()
            wasteList.forEach {
                val converter = Converter(it)
                val cost = converter.getListCost()
                val currency = converter.getListCurrency()
                val waste = converter.getListWaste()
                val wasteItemList = cost
                    .zip(waste)
                    .zip(currency) { (a, b), c ->
                        Triple(a, b, c)}
                val wasteItemDBList = mutableListOf<WasteItemDB>()
                wasteItemList.forEach { value -> wasteItemDBList.add(
                    WasteItemDB(cost = value.first,
                        wasteCategory =  value.second ?: WasteCategories.Other,
                        currency =  value.third ?: Currency.Dollar)) }
                wasteCardList.add(
                    WasteCard(wasteItemDBList,
                        it.date
                    )
                )
            }
            return wasteCardList
        }

    }

}