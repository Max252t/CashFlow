package org.example.cashflow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.cashflow.navigation.RootComponent
import org.example.cashflow.ui.ColorsUI
import org.example.cashflow.ui.scaffold_func.TopWasteBar
import org.example.cashflow.ui.scaffold_func.WasteFlButton
import org.example.cashflow.ui.scaffold_func.WasteNavBar
import org.example.cashflow.ui.screens.ChildrenScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(rootComponent: RootComponent) {
    MaterialTheme {
        val childStack by rootComponent.childStack.subscribeAsState()
        val isCreating = remember { mutableStateOf(false) }
        Box(Modifier
            .background(Brush
                .verticalGradient(0f to ColorsUI.backgroundColor,
                    1000f to Color.White))){
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopWasteBar()
            },
            bottomBar = {
                WasteNavBar(rootComponent)
            },
            floatingActionButton = {
                WasteFlButton(childStack){
                    isCreating.value = true
                }
            },
        ) { innerPadding ->
            ChildrenScreen(
                childStack,
                innerPadding,
                isCreating,
                rootComponent
            )
        }

        }
    }
}