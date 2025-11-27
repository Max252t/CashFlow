package org.example.cashflow

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cashflow.composeapp.generated.resources.Res
import cashflow.composeapp.generated.resources.addi
import cashflow.composeapp.generated.resources.home
import cashflow.composeapp.generated.resources.home_bar
import cashflow.composeapp.generated.resources.person
import cashflow.composeapp.generated.resources.person_bar
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.example.cashflow.db.WasteDao
import org.example.cashflow.navigation.BottomNavBar
import org.example.cashflow.navigation.BottomNavItem
import org.example.cashflow.navigation.RootComponent
import org.example.cashflow.network.CurrencyApi
import org.example.cashflow.ui.AccountScreen
import org.example.cashflow.ui.ColorsUI
import org.example.cashflow.ui.HomeScreen
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(rootComponent: RootComponent,
        wasteDao: WasteDao) {
    val currencyApi = CurrencyApi("https://www.cbr-xml-daily.ru/latest.js")
    currencyApi.getData()
    MaterialTheme {
        val childStack by rootComponent.childStack.subscribeAsState()
        val isCreating = remember { mutableStateOf(false) }
        Box(Modifier
            .background(Brush
                .verticalGradient(0f to ColorsUI.backgroundColor,
                    1000f to Color.White))){
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                Text("CashFlow",
                    fontSize = 23.sp,
                    modifier = Modifier
                        .systemBarsPadding()
                        .padding(start = 6.dp),
                    fontWeight = FontWeight.Bold)
            },
            bottomBar = {
                BottomNavBar(
                    listOf(
                        BottomNavItem(
                            stringResource(Res.string.home_bar),
                            vectorResource(Res.drawable.home),
                            RootComponent.Config.HomeScreen
                        ),
                        BottomNavItem(
                            stringResource(Res.string.person_bar),
                            vectorResource(Res.drawable.person),
                            RootComponent.Config.AccountScreen
                        )
                    ),
                    rootComponent = rootComponent
                )
            },
            floatingActionButton = {
                AnimatedVisibility(
                    visible = childStack.active.configuration == RootComponent.Config.HomeScreen,
                    enter = scaleIn(),
                    exit = scaleOut(),
                ) {
                    FloatingActionButton(
                        onClick = {
                            isCreating.value = true
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
            },
        ) { innerPadding ->
            Children(
                stack = childStack,
                animation = stackAnimation(slide())
            ) { child ->
                when (val instance = child.instance) {
                    is RootComponent.Child.AccountScreen -> AccountScreen(instance.component,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(innerPadding))
                    is RootComponent.Child.HomeScreen -> {
                        HomeScreen(
                            instance.component,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(innerPadding),
                            isCreating
                        )
                    }
                }
            }

        }

        }
    }
}