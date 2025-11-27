package org.example.cashflow.db.waste

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "waste")
data class Waste(
    val cost: String,
    val listWasteCategories: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val currency: String,
    val date: String
)
