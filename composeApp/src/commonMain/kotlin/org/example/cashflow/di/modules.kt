package org.example.cashflow.di

import org.example.cashflow.navigation.RootComponent
import org.example.cashflow.navigation.interfaces.RootComponentPattern
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect interface PlatformComponentContext
val sharedModule = module {
    singleOf(::RootComponent).bind< RootComponentPattern>()
}

expect val platformModule: Module