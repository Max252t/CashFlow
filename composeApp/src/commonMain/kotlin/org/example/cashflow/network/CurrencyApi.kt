package org.example.cashflow.network

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class CurrencyApi {
    private var httpClient: HttpClient
    private var url: String
    constructor(url: String){
        this.url = url
        httpClient = createUnsafeHttpClient()
    }
    fun getData(){
        CoroutineScope(Dispatchers.IO).launch {
            val data = httpClient.get(url).body<String>()
            Logger.i(data.toString(), tag = "Ktor")
            httpClient.close()

        }
    }

}
expect fun createUnsafeHttpClient(): HttpClient

@Serializable
data class CurrencyClient(
    val rates: Rates
)

@Serializable
data class Rates(
    val usd: String
)