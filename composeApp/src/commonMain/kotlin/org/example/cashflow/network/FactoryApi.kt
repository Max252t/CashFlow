package org.example.cashflow.network

class FactoryApi(val url: String) {
    fun createCashFlowApi(typeApi: TypeApi): CashFlowApi{
        val cashFlowApi = when(typeApi){
            TypeApi.CURRENCY -> CurrencyApi(url = url)
            TypeApi.CHECK -> CheckApi(url = url)
        }
        return cashFlowApi
    }
}

enum class TypeApi{
    CURRENCY, CHECK
}