package org.example.cashflow

//import io.kotest.inspectors.runTest
//import io.ktor.client.*
//import io.ktor.client.engine.mock.*
//import io.ktor.client.plugins.contentnegotiation.*
//import io.ktor.http.*
//import io.ktor.serialization.kotlinx.json.*
//
//
//import kotlinx.serialization.json.Json
//import org.example.cashflow.db.NetworkData
//import org.example.cashflow.db.currency.CbrDailyResponse
//import org.example.cashflow.network.CurrencyApi
//import org.example.cashflow.network.createUnsafeHttpClient
//import kotlin.test.AfterTest
//import kotlin.test.BeforeTest
//import kotlin.test.Test
//
//class CurrencyApiTest {
//
//    private lateinit var mockHttpClient: HttpClient
//    private lateinit var currencyApi: CurrencyApi
//
//    @BeforeTest
//    fun setUp() {
//        // Создаем mock HTTP клиент
//        mockHttpClient = HttpClient(MockEngine) {
//            install(ContentNegotiation) {
//                json(Json {
//                    ignoreUnknownKeys = true
//                    isLenient = true
//                })
//            }
//            engine {
//                addHandler { request ->
//                    when (request.url.toString()) {
//                        "https://valid-url.com" -> {
//                            val response = """
//                                {
//                                    "Date": "2024-01-15T11:30:00+03:00",
//                                    "PreviousDate": "2024-01-14T11:30:00+03:00",
//                                    "PreviousURL": "//www.cbr-xml-daily.ru/archive/2024/01/14/daily_json.js",
//                                    "Timestamp": "2024-01-15T20:00:00+03:00",
//                                    "Valute": {
//                                        "USD": {
//                                            "ID": "R01235",
//                                            "NumCode": "840",
//                                            "CharCode": "USD",
//                                            "Nominal": 1,
//                                            "Name": "Доллар США",
//                                            "Value": 90.5,
//                                            "Previous": 89.8
//                                        },
//                                        "EUR": {
//                                            "ID": "R01239",
//                                            "NumCode": "978",
//                                            "CharCode": "EUR",
//                                            "Nominal": 1,
//                                            "Name": "Евро",
//                                            "Value": 99.2,
//                                            "Previous": 98.5
//                                        }
//                                    }
//                                }
//                            """.trimIndent()
//                            respond(
//                                content = response,
//                                status = HttpStatusCode.OK,
//                                headers = headersOf(HttpHeaders.ContentType, "application/json")
//                            )
//                        }
//                        "https://invalid-json.com" -> {
//                            respond(
//                                content = "{ invalid json }",
//                                status = HttpStatusCode.OK
//                            )
//                        }
//                        "https://server-error.com" -> {
//                            respond(
//                                content = "Server Error",
//                                status = HttpStatusCode.InternalServerError
//                            )
//                        }
//                        "https://timeout.com" -> {
//                            respond(
//                                content = "",
//                                status = HttpStatusCode.OK
//                            )
//                        }
//                        else -> {
//                            respond(
//                                content = "",
//                                status = HttpStatusCode.NotFound
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    @AfterTest
//    fun tearDown() {
//        unmockkAll()
//    }
//
//    @Test
//    fun `getData should return parsed CbrDailyResponse on successful request`() = runTest {
//        // Given
//        val api = CurrencyApi("https://valid-url.com")
//
//        // Mock создание HttpClient
//        mockkStatic("org.example.cashflow.network.CurrencyApiKt")
//        every { createUnsafeHttpClient() } returns mockHttpClient
//
//        // When
//        val result = api.getData()
//
//        // Then
//        assertTrue(result is CbrDailyResponse)
//        val cbrResponse = result as CbrDailyResponse
//        assertEquals(2, cbrResponse.Valute.size)
//        assertEquals(90.5, cbrResponse.Valute["USD"]?.Value)
//        assertEquals(99.2, cbrResponse.Valute["EUR"]?.Value)
//    }
//
//    @Test
//    fun `getData should return empty CbrDailyResponse on invalid JSON`() = runTest {
//        // Given
//        val api = CurrencyApi("https://invalid-json.com")
//        mockkStatic("org.example.cashflow.network.CurrencyApiKt")
//        every { createUnsafeHttpClient() } returns mockHttpClient
//
//        // When
//        val result = api.getData()
//
//        // Then
//        assertTrue(result is CbrDailyResponse)
//        // Пустой ответ при ошибке парсинга
//        assertEquals(0, (result as CbrDailyResponse).Valute.size)
//    }
//
//    @Test
//    fun `getData should return empty CbrDailyResponse on server error`() = runTest {
//        // Given
//        val api = CurrencyApi("https://server-error.com")
//        mockkStatic("org.example.cashflow.network.CurrencyApiKt")
//        every { createUnsafeHttpClient() } returns mockHttpClient
//
//        // When
//        val result = api.getData()
//
//        // Then
//        assertTrue(result is CbrDailyResponse)
//        assertEquals(0, (result as CbrDailyResponse).Valute.size)
//    }
//
//    @Test
//    fun `getData should close HttpClient after successful request`() = runTest {
//        // Given
//        val api = CurrencyApi("https://valid-url.com")
//        val spyHttpClient = spyk(mockHttpClient)
//        mockkStatic("org.example.cashflow.network.CurrencyApiKt")
//        every { createUnsafeHttpClient() } returns spyHttpClient
//
//        // When
//        api.getData()
//
//        // Then
//        verify { spyHttpClient.close() }
//    }
//
//    @Test
//    fun `getData should close HttpClient on exception`() = runTest {
//        // Given
//        val api = CurrencyApi("https://server-error.com")
//        val spyHttpClient = spyk(mockHttpClient)
//        mockkStatic("org.example.cashflow.network.CurrencyApiKt")
//        every { createUnsafeHttpClient() } returns spyHttpClient
//
//        // When
//        api.getData()
//
//        // Then
//        verify { spyHttpClient.close() }
//    }
//
//    @Test
//    fun `constructor should set url and create HttpClient`() {
//        // Given
//        val testUrl = "https://test-url.com"
//
//        // Mock создание HttpClient
//        val mockClient = mockk<HttpClient>()
//        mockkStatic("org.example.cashflow.network.CurrencyApiKt")
//        every { createUnsafeHttpClient() } returns mockClient
//
//        // When
//        val api = CurrencyApi(testUrl)
//
//        // Then - проверяем через рефлексию, что поля установлены
//        val urlField = api::class.java.getDeclaredField("url")
//        urlField.isAccessible = true
//        assertEquals(testUrl, urlField.get(api))
//
//        val clientField = api::class.java.getDeclaredField("httpClient")
//        clientField.isAccessible = true
//        assertEquals(mockClient, clientField.get(api))
//    }
//
//    @Test
//    fun `getData should implement CashFlowApi interface`() {
//        val api = CurrencyApi("https://test.com")
//        assertTrue(api is CashFlowApi)
//    }
//}