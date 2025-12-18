package org.example.cashflow.ui.waste.create_waste

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

actual val myLang: String?
    get() = NSLocale.currentLocale.languageCode