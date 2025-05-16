package com.example.demo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ResistanceConfig
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.abs
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyStackedDragg(
    modifier: Modifier = Modifier
) {

    val cardList = listOf(1, 2, 3)
    val isExpanded = remember { mutableStateOf(false) }
    val swipeState = rememberSwipeableState(initialValue = 0)
    val cardHeight = 150.dp
    val peekHeight = 30.dp
    val density = LocalDensity.current

    // Conversiones a píxeles
    val cardHeightPx = with(density) { cardHeight.toPx() }
    val peekHeightPx = with(density) { peekHeight.toPx() }
    val thresholdPx = peekHeightPx * 2 // 60px para el threshold
    val maxDragOffset = -300f

    // Animación de expansión
    val expansionProgress by animateFloatAsState(
        targetValue = if (isExpanded.value) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "expansionAnimation"
    )

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { visible = true }

    // Control del drag finalizado
    LaunchedEffect(swipeState.isAnimationRunning) {
        snapshotFlow { swipeState.offset.value }
            .collectLatest { offset ->
                if (!swipeState.isAnimationRunning && abs(offset) > thresholdPx) {
                    isExpanded.value = true
                    swipeState.animateTo(0)
                }
            }
    }

    Box(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.Cyan)
    ) {

        for (i in cardList.indices) {

            AnimatedVisibility(
                visible = visible,
                enter = if (i != 0) {
                    fadeIn(animationSpec = tween(300)) + slideInVertically(
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

                val baseOffset = if (isExpanded.value) {
                    (cardHeightPx + peekHeightPx) * i
                } else {
                    peekHeightPx * i
                }

                // Aplicamos animación suave
                val currentOffset = if (isExpanded.value) {
                    baseOffset * expansionProgress
                } else {
                    baseOffset + if (i == (cardList.size) - 1) swipeState.offset.value else 0f
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cardHeight)
                        .offset {
                            IntOffset(
                                0,
                                currentOffset.roundToInt()
                            )
                        }
                        .then(
                            if (i == (cardList.size) - 1) Modifier.swipeable(
                                state = swipeState,
                                anchors = mapOf(0f to 0,maxDragOffset to 1),
                                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                                orientation = Orientation.Vertical,
                                resistance = ResistanceConfig(
                                    basis = cardHeightPx,
                                    factorAtMin = 3f, // Más resistencia para hacerlo más lento
                                    factorAtMax = 3f
                                )
                            ) else Modifier
                        )
                        .zIndex(i.toFloat()), // Orden correcto de apilamiento
                    elevation = CardDefaults.cardElevation(defaultElevation = i.dp),
                    border = BorderStroke(1.dp, Color.Black)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Card ${i + 1}",
                            style = MaterialTheme.typography.h1,
                            color = if (i == (cardList.size) - 1) Color.Blue else Color.Black
                        )
                    }
                }
            }

        }

    }


}