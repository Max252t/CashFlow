package org.example.cashflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.retainedComponent
import org.example.cashflow.db.waste.WasteDatabase
import org.example.cashflow.navigation.RootComponent
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val root: RootComponent by inject{ parametersOf(retainedComponent {
            it
        }) }
        val wasteDatabase by inject<WasteDatabase>()
        setContent {
            App(root, wasteDatabase.wasteDao())
        }
    }
}

