package org.example.cashflow.network

import org.example.cashflow.db.NetworkData

interface CashFlowApi {
    fun getData(): NetworkData
}
