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
import org.example.cashflow.db.waste.Waste
import org.example.cashflow.db.waste.WasteCard
import org.example.cashflow.db.waste.WasteDatabase
import org.example.cashflow.viewmodels.interfaces.WasteImpl
import org.example.cashflow.viewmodels.interfaces.WastesWork
import org.jetbrains.compose.resources.StringResource

class WasteScreenComponent(
    componentContext: ComponentContext,
    val wasteDatabase: WasteDatabase
): ComponentContext by componentContext,
    WasteImpl,
    WastesWork {
    init {
        getWastes()
    }
    private val _stateFlowWaste = MutableStateFlow(emptyList<Waste>())
    val wasteState = _stateFlowWaste.asStateFlow()

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

}