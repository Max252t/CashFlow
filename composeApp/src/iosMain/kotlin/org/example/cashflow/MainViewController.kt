package org.example.cashflow

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.example.cashflow.database.getWasteDatabase
import org.example.cashflow.di.initKoin
import org.example.cashflow.navigation.RootComponent

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    val wasteDatabase = remember {
        getWasteDatabase()
    }
    val root = remember { RootComponent(DefaultComponentContext(LifecycleRegistry()),
        wasteDatabase) }

    App(
        root
        )
}