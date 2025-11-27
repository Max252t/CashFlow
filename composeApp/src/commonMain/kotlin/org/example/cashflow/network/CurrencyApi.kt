package org.example.cashflow.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.example.cashflow.db.currency.CbrDailyResponse

class CurrencyApi {
    private var httpClient: HttpClient
    private var url: String
    constructor(url: String){
        this.url = url
        httpClient = createUnsafeHttpClient()
    }
    fun getData(): CbrDailyResponse?{
        var data: CbrDailyResponse? = null
        runBlocking {
            val ktor = async {
                val jsonData: String = httpClient.get(url).body()
                Json.decodeFromString<CbrDailyResponse>(
                    jsonData
                )
            }
            data = ktor.await()
            httpClient.close()
        }
        return data
    }

}
expect fun createUnsafeHttpClient(): HttpClient




