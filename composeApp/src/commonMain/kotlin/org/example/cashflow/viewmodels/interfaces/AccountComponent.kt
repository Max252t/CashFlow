package org.example.cashflow.viewmodels.interfaces

interface AccountComponent {
    fun getPerson()
    fun changes(changes: Changes)
}

enum class Changes{
    NAME, AVATAR
}