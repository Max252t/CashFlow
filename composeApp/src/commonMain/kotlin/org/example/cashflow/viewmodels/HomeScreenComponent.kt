package org.example.cashflow.viewmodels

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.cashflow.db.currency.CbrDailyResponse
import org.example.cashflow.db.currency.CurrencyData
import org.example.cashflow.db.waste.Waste
import org.example.cashflow.db.waste.WasteCard
import org.example.cashflow.db.waste.WasteCategories
import org.example.cashflow.db.waste.WasteDatabase
import org.example.cashflow.db.waste.WasteItemDB
import org.example.cashflow.network.CurrencyApi
import org.example.cashflow.viewmodels.interfaces.HomeComponent
import org.example.cashflow.viewmodels.interfaces.WastesWork

class HomeScreenComponent(
    componentContext: ComponentContext,
    val wasteDatabase: WasteDatabase
): ComponentContext by componentContext, HomeComponent, WastesWork {
    init {
        getWastes()
        updateCurrency()
    }
    private val _stateFlowCurrency = MutableStateFlow(CbrDailyResponse())
    val currencyState = _stateFlowCurrency.asStateFlow()

    private val _stateFlowWaste = MutableStateFlow(emptyList<Waste>())
    val wasteState = _stateFlowWaste.asStateFlow()

    private val _sumFlowState = MutableStateFlow(0)




    override fun createWaste(wasteCard: WasteCard) {
        CoroutineScope(Dispatchers.IO).launch {
            wasteCard.listWaste.forEach {
                wasteDatabase.wasteDao().upsert(
                    Waste(
                        it.cost.toString(),
                        it.wasteCategory.name,
                        currency = it.currency.name,
                        date = wasteCard.date,
                        card = wasteCard.card
                    )
                )
            }
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



    override fun updateCurrency() {
        CoroutineScope(Dispatchers.IO).launch {
            val currencyApi = CurrencyApi("https://www.cbr-xml-daily.ru/daily_json.js")
            _stateFlowCurrency.value = currencyApi.getData()
        }
    }

    fun sumWastes(wasteList: List<WasteCard>, valute: Map<String, CurrencyData>,
                  inDollars: Boolean = false): Int{
        var sum = 0
        if (valute.isNotEmpty() && wasteList.isNotEmpty()) {
            sum = wasteList.sumOf {card -> card.listWaste.sumOf { (it.cost * (if (it.currency.title!="RUB")
                valute[it.currency.title]!!.value.toFloat()
            else 1f)).toInt() }  }
            sum /= if (!inDollars) 1 else valute["USD"]!!.value.toInt()
            _sumFlowState.value = sum
            toRubles(wasteList, valute)
        }
        return sum
    }
    fun toRubles(wasteList: List<WasteCard>,
                 valute: Map<String, CurrencyData>): Map<String, Int>{
        val wasteCategories = WasteCategories.entries.toTypedArray()

        val sortCategories = mutableMapOf<String,  Int>()

        wasteCategories.forEach {
            val list = wasteList.flatMap { (listWaste, _, _) ->
                listWaste.filter { (wasteCategory, _, _) -> wasteCategory == it } }
            val listCost = list.map { (_, cost, currency) -> if (currency.title!="RUB") cost * valute[currency.title]!!.value else cost  }
            sortCategories += it.name to listCost.sumOf {cost-> cost.toInt() }
        }
        return sortCategories
    }

    companion object{
        private val _stateFlowWasteCard = MutableStateFlow(WasteCard(emptyList(), ""))
        fun updateWasteCard(wasteList: List<WasteItemDB>){
            _stateFlowWasteCard.value = WasteCard(
                listWaste = wasteList,
                date = ""
            )
        }
    }

}