package org.example.cashflow.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.ChildStack
import org.example.cashflow.navigation.RootComponent

@Composable
fun ChildrenScreen(
    childStack: ChildStack<RootComponent.Config, RootComponent.Child>,
    innerPadding: PaddingValues,
    isCreating: MutableState<Boolean>,
    rootComponent: RootComponent
) {
    Children(
        stack = childStack,
        animation = stackAnimation(slide())
    ) { child ->
        when (val instance = child.instance) {
            is RootComponent.Child.AccountScreen -> AccountScreen(
                instance.component,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            )

            is RootComponent.Child.HomeScreen -> {

                HomeScreen(
                    instance.component,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding),
                    isCreating,
                    rootComponent
                )
            }

            is RootComponent.Child.WasteScreen -> WasteScreen(
                instance.component,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            )
        }
    }
}
