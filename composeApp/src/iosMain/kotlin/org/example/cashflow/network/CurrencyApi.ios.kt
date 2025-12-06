package org.example.cashflow.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin


actual fun createUnsafeHttpClient(): HttpClient {
    return HttpClient(Darwin)
}
