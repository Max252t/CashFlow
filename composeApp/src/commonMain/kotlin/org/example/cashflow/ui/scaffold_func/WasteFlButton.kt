package org.example.cashflow.ui.scaffold_func

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import cashflow.composeapp.generated.resources.Res
import cashflow.composeapp.generated.resources.addi
import com.arkivanov.decompose.router.stack.ChildStack
import org.example.cashflow.navigation.RootComponent
import org.example.cashflow.ui.ColorsUI
import org.jetbrains.compose.resources.vectorResource

@Composable
fun WasteFlButton(
    childStack: ChildStack<RootComponent.Config, RootComponent.Child>,
    createWaste: () -> Unit
) {
    AnimatedVisibility(
        visible = childStack.active.configuration == RootComponent.Config.HomeScreen,
        enter = scaleIn(),
        exit = scaleOut(),
    ) {
        FloatingActionButton(
            onClick = {
                createWaste()
            },
            shape = CircleShape,
            containerColor = ColorsUI.purple_light,
        ) {
            Icon(
                imageVector = vectorResource(Res.drawable.addi),
                contentDescription = "add",
                tint = Color(0xFFFFFFFFF),
                modifier = Modifier.scale(1.4f)
            )
        }
    }
}