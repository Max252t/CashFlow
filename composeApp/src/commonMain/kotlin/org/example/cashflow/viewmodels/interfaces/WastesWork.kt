package org.example.cashflow.viewmodels.interfaces

import kotlinx.coroutines.flow.Flow
import org.example.cashflow.db.waste.Waste
import org.example.cashflow.db.waste.WasteCard

interface WastesWork {
    fun createWaste(wasteCard: WasteCard)
    fun getWastes(): Flow<List<Waste>>
    fun updateWaste(waste: Waste)
    fun deleteWaste(waste: Waste)
    fun convertData(listWaste: List<Waste>)
}