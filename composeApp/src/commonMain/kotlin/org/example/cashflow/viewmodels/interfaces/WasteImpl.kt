package org.example.cashflow.viewmodels.interfaces

import org.example.cashflow.db.waste.WasteCard
import org.jetbrains.compose.resources.StringResource

interface WasteImpl {
    fun statisticsWaste(listWasteCard: List<WasteCard>): List<Pair<StringResource, Float>>

}