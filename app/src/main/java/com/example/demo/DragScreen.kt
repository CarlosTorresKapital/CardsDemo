package com.example.demo

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
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    // Número de cards (puedes hacerlo dinámico)
    val cardCount = 4
    // Estado para controlar si las cards están expandidas o apiladas
    val isExpanded = remember { mutableStateOf(false) }
    // Estado para el arrastre de la card frontal
    val swipeState = rememberSwipeableState(initialValue = 0)

    // Offset entre cards cuando están apiladas
    val stackedOffset = 30.dp
    // Altura de cada card (ajusta según tu diseño)
    val cardHeight = 200.dp

    // Cuando el desplazamiento supera 30px, alternamos el estado
    LaunchedEffect(swipeState.offset.value) {
        if (abs(swipeState.offset.value) > 30 && !isExpanded.value) {
            isExpanded.value = true
        } else if (abs(swipeState.offset.value) <= 30 && isExpanded.value) {
            isExpanded.value = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight * cardCount + stackedOffset * (cardCount - 1))
    ) {
        // Dibujamos las cards desde la última hasta la primera (para el apilado correcto)
        (cardCount - 1 downTo 0).forEach { index ->
            val offset = if (isExpanded.value) {
                // Estado expandido: cards una debajo de otra
                (cardHeight + stackedOffset) * index
            } else {
                // Estado apilado: cards superpuestas con offset
                stackedOffset * index
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight)
                    .offset(y = offset)
                    // Solo hacemos draggable la card frontal (index 0)
                    .then(if (index == 0) Modifier.swipeable(
                        state = swipeState,
                        anchors = mapOf(0f to 0, 1f to 1),
                        thresholds = { _, _ -> FractionalThreshold(0.3f) },
                        orientation = Orientation.Vertical
                    ) else Modifier),
                elevation = CardDefaults.cardElevation(defaultElevation = (cardCount - index).dp)
            ) {
                // Contenido de tu card
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Card ${index + 1}")
                }
            }
        }
    }
}