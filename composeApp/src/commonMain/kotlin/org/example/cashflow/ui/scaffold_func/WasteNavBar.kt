package org.example.cashflow.ui.scaffold_func

import androidx.compose.runtime.Composable
import cashflow.composeapp.generated.resources.Res
import cashflow.composeapp.generated.resources.home
import cashflow.composeapp.generated.resources.home_bar
import cashflow.composeapp.generated.resources.person
import cashflow.composeapp.generated.resources.person_bar
import org.example.cashflow.navigation.BottomNavBar
import org.example.cashflow.navigation.BottomNavItem
import org.example.cashflow.navigation.RootComponent
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun WasteNavBar(rootComponent: RootComponent) {
    BottomNavBar(
        listOf(
            BottomNavItem(
                stringResource(Res.string.home_bar),
                vectorResource(Res.drawable.home),
                RootComponent.Config.HomeScreen
            ),
            BottomNavItem(
                stringResource(Res.string.person_bar),
                vectorResource(Res.drawable.person),
                RootComponent.Config.AccountScreen
            )
        ),
        rootComponent = rootComponent
    )
}