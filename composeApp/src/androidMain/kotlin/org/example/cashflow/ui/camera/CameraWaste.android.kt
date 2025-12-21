package org.example.cashflow.ui.camera

import android.Manifest
import android.content.Context
import androidx.camera.core.AspectRatio
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.example.cashflow.text_recognition.QRRecognitionAnalyzer

@OptIn(ExperimentalPermissionsApi::class)
@Composable
actual fun CameraWaste() {
    val cameraPermissionState: PermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    if (cameraPermissionState.status.isGranted){
        CameraAccess { detectedQR ->

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

