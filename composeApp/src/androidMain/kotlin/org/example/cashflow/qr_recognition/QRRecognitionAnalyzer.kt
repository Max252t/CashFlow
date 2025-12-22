package org.example.cashflow.qr_recognition

import android.media.Image
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class QRRecognitionAnalyzer(
    private val onDetectedQRUpdated: (String) -> Unit
): ImageAnalysis.Analyzer {
    companion object {
        const val THROTTLE_TIMEOUT_MS = 1_000L
    }
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val qrCodeScanner: BarcodeScanner = BarcodeScanning.getClient()

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(image: ImageProxy) {
        coroutineScope.launch {
            val mediaImage: Image = image.image ?: run{ image.close(); return@launch}
            val inputImage: InputImage = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)

           suspendCoroutine { continuation ->
               qrCodeScanner.process(inputImage)
                   .addOnSuccessListener { barcodes ->
                       barcodes.firstOrNull()?.rawValue?.let { qrCode ->
                           onDetectedQRUpdated(qrCode)
                       }
                   }
                   .addOnCompleteListener {
                       continuation.resume(Unit)
                   }
           }

            delay(THROTTLE_TIMEOUT_MS)
        }.invokeOnCompletion { exception ->
            exception?.printStackTrace()
            image.close()
        }
    }
}
