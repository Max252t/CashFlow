package org.example.cashflow.ui

import android.content.Context
import android.view.ViewGroup
import androidx.compose.material3.Text
import android.widget.LinearLayout
import androidx.camera.core.AspectRatio
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.example.cashflow.text_recognition.TextRecognitionAnalyzer

@Composable
actual fun CameraWaste() {
    val context: Context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController = remember { LifecycleCameraController(context) }
    var detectedText by remember { mutableStateOf("No text detected yet...") }
    fun onTextUpdated(updatedText: String){
        detectedText = updatedText
    }

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        factory = {context ->
            PreviewView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(Color.Black.value.toInt())
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }.also { previewView ->
                startTextRecognition(
                    context = context,
                    cameraController = cameraController,
                    lifecycleOwner = lifecycleOwner,
                    previewView = previewView,
                    onDetectedTextUpdated = ::onTextUpdated
                )
            }
        }
    )
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        text = detectedText,
    )
}

@Suppress("DEPRECATION")
private fun startTextRecognition(
    context: Context,
    cameraController: LifecycleCameraController,
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView,
    onDetectedTextUpdated: (String) -> Unit
) {
    cameraController.imageAnalysisTargetSize = CameraController.OutputSize(AspectRatio.RATIO_16_9)
    cameraController.setImageAnalysisAnalyzer(
        ContextCompat.getMainExecutor(context),
        TextRecognitionAnalyzer(onDetectedTextUpdated = onDetectedTextUpdated)
    )
    cameraController.bindToLifecycle(lifecycleOwner)
    previewView.controller = cameraController
}
