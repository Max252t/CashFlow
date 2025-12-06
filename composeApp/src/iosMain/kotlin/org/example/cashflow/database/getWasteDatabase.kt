package org.example.cashflow.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.example.cashflow.db.waste.WasteDatabase
import platform.Foundation.NSHomeDirectory

fun getWasteDatabase(): WasteDatabase {
    val dbFile = NSHomeDirectory() + "/waste.db"
    return Room.databaseBuilder<WasteDatabase>(
        name = dbFile,
        factory = { WasteDatabase::class.instantiateImpl()}
    )
        .fallbackToDestructiveMigration(false)
        .setDriver(BundledSQLiteDriver())
        .build()
}