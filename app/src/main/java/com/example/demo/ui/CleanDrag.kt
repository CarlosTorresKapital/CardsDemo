package com.example.demo.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeableState
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.demo.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Preview(showBackground = true)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CleanDrag(
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    val cardsToPrint = listOf(1, 2, 3, 4)

    val cardHeight = 190.dp
    val cardHeightPx = with(LocalDensity.current) {
        cardHeight.toPx()
    }

    //Swipe state for every single card item
    val listSwipeState = cardsToPrint.map {
        rememberSwipeableState(0)
    }


    val defaultOffset = 30.dp
    val defaultOffsetPx = with(LocalDensity.current) {
        defaultOffset.toPx()
    }

    val maxOffsetPx = cardHeightPx * (cardsToPrint.size - 1) // El ancla máxima del swipe
    val initialOffsetDp = 64.dp
    val finalOffsetDp = 16.dp

    val initialOffsetPx = with(LocalDensity.current) { initialOffsetDp.toPx() }
    val finalOffsetPx = with(LocalDensity.current) { finalOffsetDp.toPx() }

    val currentDrag = listSwipeState.last().offset.value.coerceIn(0f, maxOffsetPx)

    val interpolatedOffsetPx = remember(currentDrag) {
        initialOffsetPx - (currentDrag / maxOffsetPx) * (initialOffsetPx - finalOffsetPx)
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .animateContentSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {

        val boxHeightDP = this.maxHeight
        val containerHeightPx = with(LocalDensity.current) {
            boxHeightDP.toPx()
        }

        val swipeableState = rememberSwipeableState(0)
        val anchors = if (cardsToPrint.size == 1) {
            mapOf(
                0f to 0
            )
        } else {
            mapOf(
                0f to 0,
                (cardHeightPx) * (cardsToPrint.size - 1) to 1
            )
        }

        val coroutineScope = rememberCoroutineScope()

        var isExpanded by remember {
            mutableStateOf(false)
        }

        var cardsVisibility by remember {
            mutableStateOf(false)
        }

        LaunchedEffect(cardsVisibility) {
            delay(200)
            cardsVisibility = true
        }

        cardsToPrint.indices.forEach { index ->

            AnimatedVisibility(
                modifier = Modifier.fillMaxSize(),
                visible = cardsVisibility,
                enter = if (index != 0) { //La primar tarjeta no hace la animacion
                    fadeIn(tween(500)) + slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                } else {
                    fadeIn(tween(500))
                }
            ){

                Column {

                    if (index != 0) {
                        DynamicHeightSpacer(
                            index = index,
                            cardsToPrint = cardsToPrint,
                            listSwipeState = listSwipeState,
                            defaultOffsetPx = interpolatedOffsetPx
                        )
                    }


                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(cardHeight)
                            .then(
                                if (cardsToPrint.lastIndex == index) {
                                    Modifier
                                        .pointerInput(Unit) {
                                            detectVerticalDragGestures(
                                                onDragEnd = {
                                                    coroutineScope.launch {
                                                        listSwipeState[index].animateTo(
                                                            listSwipeState[index].currentValue
                                                        )
                                                    }
                                                }
                                            ) { change, dragAmount ->
                                                val newOffset =
                                                    listSwipeState[index].offset.value + dragAmount
                                                listSwipeState[index].performDrag(newOffset)
                                            }
                                        }
                                        .swipeable(
                                            orientation = Orientation.Vertical,
                                            state = listSwipeState[index],
                                            anchors = anchors,
                                            thresholds = { _, _ -> FractionalThreshold(0.3f) }
                                        )
                                } else Modifier
                            ),
                        elevation = 10.dp,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color.Black),
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    Toast.makeText(context, "$index", Toast.LENGTH_SHORT).show()
                                }
                        ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize(),
                                painter = painterResource(R.drawable.card_mx_tdc),
                                contentDescription = "Card",
                                contentScale = ContentScale.FillBounds
                            )
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = "Card $index",
                                color = Color.White
                            )
                        }

                    }
                }
            }



        }

    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DynamicHeightSpacer(
    index: Int,
    cardsToPrint: List<Int>, // Asume que tienes una lista de datos de cartas
    listSwipeState: List<SwipeableState<Int>>, // Estados de swipe de cada carta
    defaultOffsetPx: Float // Offset base en píxeles
) {
    val density = LocalDensity.current

    var collapseHeight by remember { mutableStateOf(false) }

    var animateInitially by remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        collapseHeight = true
        delay(200)
        animateInitially = false
    }

    // Cálculo del height dinámico (similar al offset original)
    val heightDp = remember(index, cardsToPrint, listSwipeState, defaultOffsetPx) {
        derivedStateOf {
            val lastIndex = cardsToPrint.lastIndex
            val lastOffset = listSwipeState[lastIndex].offset.value

            val unitOffset = if (lastIndex > 0) lastOffset / lastIndex else 0f
            val reactiveOffset = unitOffset * index
            val baseOffset = defaultOffsetPx * index

            val totalOffsetPx = reactiveOffset + baseOffset
            with(density) { totalOffsetPx.toDp() } // Convertir píxeles a Dp
        }
    }.value

    val heightDemo by animateDpAsState(
        targetValue = if (collapseHeight) heightDp.value.dp else 500.dp,
        animationSpec = if (animateInitially) {
            tween(durationMillis = 500)
        } else {
            snap() // Cambio instantáneo, sin animación
        },
        label = "heightAnim"
    )

    Spacer(modifier = Modifier.height(heightDemo))

}