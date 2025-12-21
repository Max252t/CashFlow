package org.example.cashflow.db.check
import kotlinx.serialization.Serializable
import org.example.cashflow.db.NetworkData

@Serializable
data class ReceiptResponse(
    val document: Document? = null
) : NetworkData()

@Serializable
data class Document(
    val receipt: Receipt
)

@Serializable
data class Receipt(
    val operationType: Int,
    val fiscalSign: Long,
    val dateTime: String? = null,
    val rawData: String,
    val totalSum: Int,
    val nds10: Int? = null,
    val userInn: String? = null,
    val taxationType: Int? = null,
    val operator: String? = null,
    val fiscalDocumentNumber: Int? = null,
    val properties: List<Property> = emptyList(),
    val receiptCode: Int? = null,
    val requestNumber: Int? = null,
    val user: String? = null,
    val kktRegId: String? = null,
    val fiscalDriveNumber: String? = null,
    val items: List<ReceiptItem> = emptyList(),
    val retailPlaceAddress: String? = null,
    val cashTotalSum: Int? = null,
    val shiftNumber: Int? = null
)

@Serializable
data class Property(
    val value: String,
    val key: String
)

@Serializable
data class ReceiptItem(
    val sum: Int,
    val price: Int,
    val name: String,
    val quantity: Int,
    val nds10: Int? = null
)