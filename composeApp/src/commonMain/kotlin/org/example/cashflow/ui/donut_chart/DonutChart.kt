package org.example.cashflow.ui.donut_chart

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun DonutChart(
    data: Map<String, Int>,
    radius: Dp = 90.dp,
    chartBarWidth: Dp = 20.dp,
    animDuration: Int = 600
){
    val totalSum = data.values.sum()
    val floatValue = mutableListOf<Float>()
    data.values.forEachIndexed { index, item ->
        floatValue.add(index, 360*item.toFloat() / totalSum.toFloat())
    }

    val colors = mutableListOf(
        Color(0xFF9963FA),
        Color(0xFF54C4EF),
        Color(0xFF4A49D5),
        Color(0xFFE68ACC),
        Color(0xFF4C3B77),
        Color(0xFF9963FA),

        Color(0xFF4A49D5),
        Color(0xFFE68ACC),
        Color(0xFF4C3B77),
        Color(0xFF9963FA),
    )
    var animationPlayed by remember { mutableStateOf(false) }

    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radius.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(if (animationPlayed) animateSize.dp + 40.dp else animateSize.dp)
                    .clickable {
                        animationPlayed = !animationPlayed
                    },
                contentAlignment = Alignment.Center
            ) {
                DrawDonutChart(
                    radius,
                    animateRotation,
                    floatValue,
                    colors,
                    chartBarWidth,
                    animationPlayed,
                    data
                )
            }


    }

}
expect fun DrawScope.drawTextItem(
    text: String,
    x: Float,
    y: Float,
    color: Color,
    textSize: TextUnit
)
