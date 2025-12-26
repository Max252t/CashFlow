package org.example.cashflow.network

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.example.cashflow.db.NetworkData
import org.example.cashflow.db.currency.CbrDailyResponse

class CurrencyApi: CashFlowApi {
    private var httpClient: HttpClient
    private var url: String
    constructor(url: String){
        this.url = url
        httpClient = createUnsafeHttpClient()
    }
    override fun getData(): NetworkData{
        var data = CbrDailyResponse()
        val exc = runCatching {
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
        }
        exc.onFailure {
            httpClient.close()
            Logger.e(messageString = it.message ?: "", tag = "Currency")
        }
        return data
    }

    override fun postData(path: String): NetworkData {
        return CbrDailyResponse()
    }

}
expect fun createUnsafeHttpClient(): HttpClient
