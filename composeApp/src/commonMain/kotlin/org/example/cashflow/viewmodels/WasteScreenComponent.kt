package org.example.cashflow.viewmodels

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.cashflow.db.convertDB.Converter
import org.example.cashflow.db.currency.CbrDailyResponse
import org.example.cashflow.db.waste.Waste
import org.example.cashflow.db.waste.WasteCard
import org.example.cashflow.db.waste.WasteCategories
import org.example.cashflow.db.waste.WasteDatabase
import org.example.cashflow.network.FactoryApi
import org.example.cashflow.network.TypeApi
import org.example.cashflow.viewmodels.interfaces.WasteImpl
import org.example.cashflow.viewmodels.interfaces.WastesWork
import org.jetbrains.compose.resources.StringResource

class WasteScreenComponent(
    componentContext: ComponentContext,
    val wasteDatabase: WasteDatabase,
    val currencyData: CbrDailyResponse
): ComponentContext by componentContext,
    WasteImpl,
    WastesWork {
    init {
        getWastes()
    }
    private val _stateFlowWaste = MutableStateFlow(emptyList<Waste>())
    val wasteState = _stateFlowWaste.asStateFlow()
    private val _stateFlowCurrency = MutableStateFlow(CbrDailyResponse())
    val currencyState = _stateFlowCurrency.asStateFlow()

    override fun createWaste(wasteCard: WasteCard) {
        CoroutineScope(Dispatchers.IO).launch {
            wasteDatabase.wasteDao().upsert(
                Converter.convertWasteCard(wasteCard)
            )
        }
    }
    override fun getWastes(): StateFlow<List<Waste>> {
        CoroutineScope(Dispatchers.IO).launch {
            wasteDatabase.wasteDao().getAllWaste().collect { data ->
                _stateFlowWaste.value = data
            }
        }
        return wasteState
    }


    override fun updateWaste(waste: Waste) {
        CoroutineScope(Dispatchers.IO).launch {
            wasteDatabase.wasteDao().upsert(waste)
        }

    }

    override fun deleteWaste(waste: Waste) {
        CoroutineScope(Dispatchers.IO).launch {
            wasteDatabase.wasteDao().delete(waste)
        }
        getWastes()
    }

    override fun convertData(listWaste: List<Waste>) {

    }
    override fun statisticsWaste(listWasteCard: List<WasteCard>): List<Pair<StringResource, Float>> {
        val statistics = mutableListOf<Pair<StringResource, Float>>()
        val sumWastes = mutableListOf<Float>()
        listWasteCard.forEach {
            sumWastes.add(
                it.listWaste.sumOf {
                        item -> item.cost.toDouble()
                }.toFloat()
            )
        }
        return emptyList()
    }

    fun toRubles(): Map<String, Int>{
        if (currencyData!= CbrDailyResponse()) {
            val wasteList = Converter.convertWaste(_stateFlowWaste.value)
            _stateFlowWaste.value
            val valute = currencyData.valute
            val wasteCategories = WasteCategories.entries.toTypedArray()

            val sortCategories = mutableMapOf<String, Int>()

            wasteCategories.forEach {
                val list = wasteList.flatMap { (listWaste, _, _) ->
                    listWaste.filter { (wasteCategory, _, _) -> wasteCategory == it }
                }
                val listCost =
                    list.map { (_, cost, currency) -> if (currency.title != "RUB") cost * valute[currency.title]!!.value else cost }
                sortCategories += it.name to listCost.sumOf { cost -> cost.toInt() }
            }
            val iterator = sortCategories.iterator()
            while (iterator.hasNext()) {
                val (_, value) = iterator.next()
                if (value == 0) {
                    iterator.remove()
                }
            }
            return sortCategories
        }

        return mapOf("Error" to 1)
    }
    fun updateCurrency() {
        CoroutineScope(Dispatchers.IO).launch {
            val currencyApi = FactoryApi("https://www.cbr-xml-daily.ru/daily_json.js")
                .createCashFlowApi(TypeApi.CURRENCY)
            _stateFlowCurrency.value = currencyApi.getData() as CbrDailyResponse
        }
    }

}