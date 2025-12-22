package org.example.cashflow.ui.camera

import androidx.compose.runtime.Composable
import org.example.cashflow.db.waste.WasteItemDB

@Composable
expect fun CameraWaste(returnWaste: (WasteItemDB) -> Unit)