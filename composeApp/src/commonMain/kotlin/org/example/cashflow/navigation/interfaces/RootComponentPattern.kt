package org.example.cashflow.navigation.interfaces

import org.example.cashflow.navigation.RootComponent

interface RootComponentPattern {
    fun navigateTo(route: RootComponent.Config)
    fun getRoute(): String
    var currentRoute: String
    fun navigateAHead(route: RootComponent.Config)
}