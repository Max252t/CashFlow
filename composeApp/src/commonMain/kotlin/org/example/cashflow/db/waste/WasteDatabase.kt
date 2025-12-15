package org.example.cashflow.db.waste

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Waste::class],
    version = 3,
//    autoMigrations = [
//        AutoMigration(from = 2, to = 3)
//    ]
)
abstract class WasteDatabase: RoomDatabase() {
    abstract fun wasteDao(): WasteDao

}
