package org.example.cashflow.network


import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.example.cashflow.db.NetworkData
import org.example.cashflow.db.check.ReceiptResponse

class CheckApi: CashFlowApi {
    private var httpClient: HttpClient
    private var url: String
    constructor(url: String){
        this.url = url
        httpClient = HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                    explicitNulls = false
                    coerceInputValues = true
                })
            }
        }
    }
    override fun getData(): NetworkData {
        var data = ReceiptResponse()
        runBlocking {
            val ktor = async {
                val jsonData: String = httpClient.get(url).body()
                Json.decodeFromString<ReceiptResponse>(
                    jsonData
                )
            }
            data = ktor.await()
            httpClient.close()
        }
        return data
    }
}