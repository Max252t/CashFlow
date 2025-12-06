package org.example.cashflow.ui.waste

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.cashflow.db.waste.WasteCategories
import org.jetbrains.compose.resources.stringResource

@Stable
@Composable
fun CategoryCard(wasteCategory: WasteCategories,
                 color: Color
                 ){
    Card(elevation = CardDefaults.elevatedCardElevation(7.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF6F6F6)
        ),
        modifier = Modifier
            .height(100.dp)
            .widthIn(min = 90.dp)
            .padding(5.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(3.dp)
            ) {
                Icon(
                    wasteCategory.icon,
                    contentDescription = wasteCategory.name,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(color),
                    tint = Color.White
                )
                Text(
                    stringResource(wasteCategory.title),
                    fontSize = 16.sp
                )
            }
        }
    }
}