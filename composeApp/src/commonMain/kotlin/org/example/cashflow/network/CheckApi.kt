package org.example.cashflow.network


import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.example.cashflow.db.NetworkData
import org.example.cashflow.db.check.CheckResponse


class CheckApi: CashFlowApi {
    private var httpClient: HttpClient
    private var baseUrl: String
    private var token: String
    constructor(url: String, token: String){
        this.baseUrl = url
        this.token = token
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
        return CheckResponse(code = 0, first = 0)
    }

    override fun postData(path: String): NetworkData {
        var result = CheckResponse(code = 0, first = 0)

        runBlocking {
            val ktor = async {
                httpClient.post("$baseUrl/api/v1/check/get") {
                    setBody(
                        MultiPartFormDataContent(
                            formData {
                                append("token", token)
                                append("qrurl", path)
                            }
                        )
                    )
                }.body<CheckResponse>()
            }
            result = ktor.await()
            httpClient.close()
        }

        return result
    }


}