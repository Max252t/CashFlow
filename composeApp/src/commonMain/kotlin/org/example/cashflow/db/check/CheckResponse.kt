package org.example.cashflow.db.check
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.cashflow.db.NetworkData

@Serializable
data class CheckResponse(
    val code: Int,
    val first: Int,
    val data: CheckData? = null,
    val request: CheckRequest? = null
) : NetworkData()

@Serializable
data class CheckData(
    val json: CheckJson? = null,
    val html: String? = null
)

@Serializable
data class CheckJson(
    val user: String? = null,
    val retailPlaceAddress: String? = null,
    val userInn: String? = null,
    val ticketDate: String? = null,
    val requestNumber: String? = null,
    val shiftNumber: String? = null,
    val operator: String? = null,
    val operationType: Int? = null,
    val items: List<CheckItem> = emptyList(),
    val nds18: Long? = null,
    val nds10: Long? = null,
    val nds0: Long? = null,
    val ndsNo: Long? = null,
    val totalSum: Long? = null,
    val cashTotalSum: Long? = null,
    val ecashTotalSum: Long? = null,
    val taxationType: Int? = null,
    val kktRegId: String? = null,
    val kktNumber: String? = null,
    val fiscalDriveNumber: String? = null,
    val fiscalDocumentNumber: String? = null,
    val fiscalSign: String? = null,
    val userData: Map<String, String>? = null
)

@Serializable
data class CheckItem(
    val name: String,
    val price: Long,
    val quantity: Double,
    val sum: Long
)

@Serializable
data class CheckRequest(
    val qrurl: String? = null,
    val qrfile: String? = null,
    val qrraw: String? = null,
    val manual: CheckManual? = null
)

@Serializable
data class CheckManual(
    val fn: String? = null,
    val fd: String? = null,
    val fp: String? = null,
    @SerialName("check_time")
    val checkTime: String? = null,
    val type: Int? = null,
    val sum: Double? = null
)