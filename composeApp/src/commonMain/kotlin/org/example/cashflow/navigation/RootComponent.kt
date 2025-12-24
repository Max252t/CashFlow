package org.example.cashflow.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import kotlinx.serialization.Serializable
import org.example.cashflow.db.currency.CbrDailyResponse

import org.example.cashflow.db.waste.WasteDatabase
import org.example.cashflow.navigation.RootComponent.Child.*
import org.example.cashflow.navigation.interfaces.RootComponentPattern
import org.example.cashflow.viewmodels.AccountScreenComponent
import org.example.cashflow.viewmodels.HomeScreenComponent
import org.example.cashflow.viewmodels.WasteScreenComponent

class RootComponent(
    componentContext: ComponentContext,
    val wasteDatabase: WasteDatabase
): ComponentContext by componentContext, RootComponentPattern {
    private val navigation = StackNavigation<Config>()
    val childStack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.HomeScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        config: Config,
        context: ComponentContext
    ): Child {
        return when(config){
            is Config.AccountScreen -> AccountScreen(
                AccountScreenComponent(context)
            )
            Config.HomeScreen -> {
                HomeScreen(
                    HomeScreenComponent(
                        context,
                        wasteDatabase,
                        onNavigateToWaste = {currencyData ->
                            navigation.replaceCurrent(Config.WasteScreen(currencyData))
                        }
                    )
                )
            }

           is Config.WasteScreen -> WasteScreen(
                WasteScreenComponent(
                    context,
                    wasteDatabase,
                    config.cbrDailyResponse
                )
            )
        }
    }
   override fun navigateTo(route: Config){
       navigation.replaceCurrent(route)
    }

    override fun getRoute(): String {
        return currentRoute
    }

    override var currentRoute = "home"
    override fun navigateAHead(route: Config) {
    }

    sealed class Child{
        data class HomeScreen(val component: HomeScreenComponent): Child()
        data class AccountScreen(val component: AccountScreenComponent): Child()
        data class WasteScreen(val component: WasteScreenComponent): Child()


    }

    @Serializable
    sealed class Config {
        @Serializable
        data object HomeScreen: Config()

        @Serializable
        data object AccountScreen: Config()
        @Serializable
        data class WasteScreen(val cbrDailyResponse: CbrDailyResponse): Config()
    }
}