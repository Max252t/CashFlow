package org.example.cashflow.db.waste

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Waste::class],
    version = 2
)
abstract class WasteDatabase: RoomDatabase() {
    abstract fun wasteDao(): WasteDao

}
