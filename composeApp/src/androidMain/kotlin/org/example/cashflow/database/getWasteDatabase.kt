package org.example.cashflow.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.example.cashflow.db.waste.WasteDatabase

fun getWasteDatabase(context: Context): WasteDatabase{
    val dbFile = context.getDatabasePath("waste.db")
    return Room.databaseBuilder<WasteDatabase>(
        context.applicationContext,
        dbFile.absolutePath,
    )
        .fallbackToDestructiveMigration(false)
        .setDriver(BundledSQLiteDriver())
        .build()
}