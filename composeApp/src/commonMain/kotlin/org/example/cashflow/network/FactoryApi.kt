package org.example.cashflow.network

class FactoryApi(val url: String) {
    fun createCashFlowApi(typeApi: TypeApi): CashFlowApi{
        val cashFlowApi = when(typeApi){
            TypeApi.CURRENCY -> CurrencyApi(url = url)
            TypeApi.CHECK -> CheckApi(url = url, token = "36964.hN8iwesgkUq6l1DHY")
        }
        return cashFlowApi
    }
}

enum class TypeApi{
    CURRENCY, CHECK
}