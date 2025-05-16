package com.example.demo

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StackedCards(
    navToDrag: () -> Unit
) {

    val context = LocalContext.current

    val cardNumbers = listOf("1234", "9876", "2345", "5534", "9999")


    var expanded by remember { mutableStateOf(false) }

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { visible = true }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Stacked Cards")
                },
                actions = {
                    IconButton(
                        onClick = navToDrag
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "Check")
                    }
                }
            )
        }
    ) { paddingValues ->

        BoxWithConstraints(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            val maxWidth = (this.maxWidth / 4) * 3

            for (i in cardNumbers.indices) {

                AnimatedVisibility(
                    visible = visible,
                    enter = if (i != 0) {
                        fadeIn(animationSpec = tween(1000)) + slideInVertically(
                            // Aparece desde abajo
                            initialOffsetY = { it },
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessVeryLow
                            )
                        )
                    } else {
                        fadeIn()
                    }
                ) {

                    val isExpandedHeight by animateDpAsState(
                        targetValue = if (expanded) (i * 140).dp else 0.dp,
                        label = "CardHeight"
                    )
                    val spacerHeight = isExpandedHeight + (i * 69).dp

                    // ⭐ Swipe gesture solo en la tarjeta correspondiente
                    val shouldHandleSwipe = if (!expanded) {
                        i == cardNumbers.lastIndex // Última visible (arriba de la pila)
                    } else {
                        i == cardNumbers.lastIndex // Última expandida (abajo)
                    }

                    val swipeModifier = if (shouldHandleSwipe) {
                        Modifier.pointerInput(expanded) {
                            detectVerticalDragGestures { _, dragAmount ->
                                if (!expanded && dragAmount > 20) {
                                    // ⭐ Swipe hacia abajo para expandir
                                    expanded = true
                                } else if (expanded && dragAmount < -20) {
                                    // ⭐ Swipe hacia arriba para colapsar
                                    expanded = false
                                }
                            }
                        }
                    } else {
                        Modifier
                    }

                    Column() {
                        Spacer(modifier = Modifier.height(spacerHeight))

                        KapitalCard(
                            modifier = Modifier
                                .then(swipeModifier)
                                .fillMaxWidth()
                                .clickable {
                                    Toast.makeText(
                                        context,
                                        cardNumbers[i].toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    //expanded = !expanded
                                },
                            cardNumber = i.toString(),
                            type = if (i % 2 == 0) 1 else 2,
                            isHidden = i == cardNumbers.lastIndex - 1 || i == cardNumbers.lastIndex - 2
                        )

                    }
                }

            }

            if (expanded) {
                val boxHeigjt by animateDpAsState(
                    targetValue = if (expanded) 200.dp else 0.dp,
                    label = "CardHeight"
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        //.fillMaxWidth()
                        .width(maxWidth)
                        .height(boxHeigjt)
                        .clickable {
                            Toast.makeText(
                                context,
                                cardNumbers.last().toString(),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                )
            }
        }
    }

}