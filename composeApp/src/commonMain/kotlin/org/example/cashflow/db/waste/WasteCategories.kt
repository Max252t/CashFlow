package org.example.cashflow.db.waste

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.AltRoute
import androidx.compose.material.icons.filled.Attractions
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CardTravel
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shop
import androidx.compose.ui.graphics.vector.ImageVector
import cashflow.composeapp.generated.resources.Res
import cashflow.composeapp.generated.resources.bills
import cashflow.composeapp.generated.resources.entertainment
import cashflow.composeapp.generated.resources.food
import cashflow.composeapp.generated.resources.health
import cashflow.composeapp.generated.resources.housing
import cashflow.composeapp.generated.resources.other
import cashflow.composeapp.generated.resources.personal
import cashflow.composeapp.generated.resources.shopping
import cashflow.composeapp.generated.resources.transportation
import cashflow.composeapp.generated.resources.travel
import org.jetbrains.compose.resources.StringResource

enum class WasteCategories(val title: StringResource, val icon: ImageVector) {
    Housing(Res.string.housing, Icons.Default.House),
    Transport(Res.string.transportation, Icons.Default.Business),
    Food(Res.string.food, Icons.Default.Fastfood),
    Shop(Res.string.shopping, Icons.Default.Shop),
    Entertainment(Res.string.entertainment, Icons.Default.Attractions),
    Health(Res.string.health, Icons.Default.MonitorHeart),
    Bills(Res.string.bills, Icons.Default.Paid),
    Travel(Res.string.travel, Icons.Default.CardTravel),
    Personal(Res.string.personal, Icons.Default.Person),
    Other(Res.string.other, Icons.AutoMirrored.Filled.AltRoute);

}