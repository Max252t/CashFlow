package org.example.cashflow.db.currency

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.cashflow.db.NetworkData

@Serializable
data class CbrDailyResponse(
    @SerialName("Date")
    val date: String = "",

    @SerialName("PreviousDate")
    val previousDate: String = "",

    @SerialName("PreviousURL")
    val previousURL: String = "",

    @SerialName("Timestamp")
    val timestamp: String = "",

    @SerialName("Valute")
    val valute: Map<String, CurrencyData> = emptyMap()
) : NetworkData()


