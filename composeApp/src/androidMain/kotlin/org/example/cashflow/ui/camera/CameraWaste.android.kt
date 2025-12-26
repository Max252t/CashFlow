package org.example.cashflow.ui.camera

import android.Manifest
import android.content.Context
import androidx.camera.core.AspectRatio
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.example.cashflow.db.convertDB.ConverterCheck
import org.example.cashflow.db.waste.WasteItemDB
import org.example.cashflow.network.FactoryApi
import org.example.cashflow.network.TypeApi
import org.example.cashflow.qr_recognition.QRRecognitionAnalyzer
import org.example.cashflow.ui.waste.create_waste.EditItem

@OptIn(ExperimentalPermissionsApi::class)
@Composable
actual fun CameraWaste(
    returnWaste: (WasteItemDB) -> Unit
) {
    var qrText by remember { mutableStateOf("No qr detected yet...") }
    val cameraPermissionState: PermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    if (cameraPermissionState.status.isGranted){
        if (qrText == "No qr detected yet...") {
            CameraAccess { detectedQR ->
                qrText = detectedQR
            }
        } else{
            val checkApi = FactoryApi("https://proverkacheka.com").createCashFlowApi(TypeApi.CHECK)
            val result = checkApi.postData(qrText)
            val converter = ConverterCheck(qrText)
            EditItem(wasteDefault = converter.getCost().toString(),
                onCreateItem =  { cost, currency, wasteCategories ->
                    val wasteItemDB = WasteItemDB(wasteCategories, cost, currency)
                    returnWaste(wasteItemDB)
                })
        }
    } else{
        NoPermissionContent(
            cameraPermissionState::launchPermissionRequest
        )
    }

}

@Suppress("DEPRECATION")
internal fun startQRRecognition(
    context: Context,
    cameraController: LifecycleCameraController,
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView,
    onDetectedQrUpdated: (String) -> Unit
) {
    cameraController.imageAnalysisTargetSize = CameraController.OutputSize(AspectRatio.RATIO_16_9)
    cameraController.setImageAnalysisAnalyzer(
        ContextCompat.getMainExecutor(context),
        QRRecognitionAnalyzer(onDetectedQRUpdated = onDetectedQrUpdated)
    )
    cameraController.bindToLifecycle(lifecycleOwner)
    previewView.controller = cameraController
}

