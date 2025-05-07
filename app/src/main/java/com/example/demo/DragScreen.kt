package com.example.demo

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.demo.ui.theme.PrimaryBlack
import kotlin.math.abs
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DragScreen() {

    val width = 96.dp
    val squareSize = 48.dp

    val swipeState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1) // Puntos de anclaje (px) -> estados


    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .width(width)
                .swipeable(
                    state = swipeState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) },
                    orientation = Orientation.Horizontal
                )
                .background(Color.LightGray)
        ) {
            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(swipeState.offset.value.roundToInt(), 0)
                    }
                    .size(squareSize)
                    .background(PrimaryBlack)
            )
        }

        StackedDraggableCards()

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StackedDraggableCards() {
    val cardCount = 3
    val isExpanded = remember { mutableStateOf(false) }
    val swipeState = rememberSwipeableState(initialValue = 0)
    val cardHeight = 200.dp
    val stackedOffset = 30.dp
    val density = LocalDensity.current

    // Convertimos valores a píxeles
    val thresholdPx = with(density) { 30.dp.toPx() }
    val cardHeightPx = with(density) { cardHeight.toPx() }
    val stackedOffsetPx = with(density) { stackedOffset.toPx() }

    // Animación para el offset de expansión
    val expansionProgress by animateFloatAsState(
        targetValue = if (isExpanded.value) 1f else 0f,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "expansionProgress"
    )

    // Efecto para detectar cuando superamos el threshold
    LaunchedEffect(swipeState.offset.value) {
        if (abs(swipeState.offset.value) > thresholdPx && !isExpanded.value) {
            isExpanded.value = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight * cardCount + stackedOffset * (cardCount - 1))
    ) {
        (cardCount - 1 downTo 0).forEach { index ->
            // Calculamos la posición base sin drag
            val baseOffset = if (isExpanded.value) {
                (cardHeightPx + stackedOffsetPx) * index
            } else {
                stackedOffsetPx * index
            }

            // Aplicamos el progreso de animación para suavizar la transición
            val animatedOffset = if (!isExpanded.value) {
                baseOffset + swipeState.offset.value
            } else {
                baseOffset * expansionProgress
            }

            // Solo la card superior es draggable
            val dragModifier = if (index == 0 && !isExpanded.value) {
                Modifier.swipeable(
                    state = swipeState,
                    anchors = mapOf(0f to 0),
                    thresholds = { _, _ -> FractionalThreshold(0.3f) },
                    orientation = Orientation.Vertical,
                    resistance = ResistanceConfig(
                        basis = cardHeightPx,
                        factorAtMin = 0.5f,
                        factorAtMax = 0.5f
                    )
                )
            } else Modifier

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight)
                    .offset {
                        IntOffset(
                            0,
                            animatedOffset.roundToInt()
                        )
                    }
                    .then(dragModifier),
                elevation = CardDefaults.cardElevation((cardCount - index).dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Card ${index + 1}",
                        style = MaterialTheme.typography.h1
                    )
                }
            }
        }
    }
}