package org.example.cashflow.viewmodels

import com.arkivanov.decompose.ComponentContext
import org.example.cashflow.viewmodels.interfaces.AccountComponent
import org.example.cashflow.viewmodels.interfaces.Changes

class AccountScreenComponent(
    componentContext: ComponentContext
): ComponentContext by componentContext, AccountComponent {
    override fun getPerson() {
        TODO("Not yet implemented")
    }

    override fun changes(changes: Changes) {
        when(changes){
            Changes.AVATAR -> {}
            Changes.NAME -> {}
        }
    }

}