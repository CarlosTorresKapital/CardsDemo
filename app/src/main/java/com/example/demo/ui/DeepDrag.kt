package com.example.demo.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.demo.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardStack(
    modifier: Modifier = Modifier
) {

    val cardCount = listOf(1,2,3)

    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        val boxHeightDP = this.maxHeight
        val containerHeightPx = with(LocalDensity.current) { boxHeightDP.toPx() }

        val cardHeight = 190.dp
        val cardHeightPx = with(LocalDensity.current) { cardHeight.toPx() }
        val cardOffset = 30.dp
        val cardOffsetPx = with(LocalDensity.current) { cardOffset.toPx() }

        val swipeableState = rememberSwipeableState(0)
        val anchors = mapOf(
            0f to 0,
            containerHeightPx - cardHeightPx to 1
        )

        val coroutineScope = rememberCoroutineScope()
        val isExpanded = swipeableState.offset.value > with(LocalDensity.current) { cardOffset.toPx() }

        // Render all cards in reverse order (back first, front last)
        (cardCount.indices).forEach { index ->
            val offset = if (isExpanded) {
                // In expanded mode, cards are stacked vertically
                (index * (cardHeight + 10.dp).value).dp
            } else {
                // In stacked mode, cards overlap with 30dp offset
                (index * cardOffset.value).dp
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight)
                    .offset(y = if (index == cardCount.lastIndex) {
                        // Only the top card is swipeable
                        offset + swipeableState.offset.value.toInt().dp
                    } else {
                        offset
                    })
                    .then(if (index == cardCount.lastIndex) {
                        // Only make the top card swipeable
                        Modifier
                            .pointerInput(Unit) {
                                detectVerticalDragGestures(
                                    onDragEnd = {
                                        coroutineScope.launch {
                                            if (swipeableState.offset.value > cardOffsetPx) {
                                                // If dragged enough, expand all cards
                                                swipeableState.animateTo(1)
                                            } else {
                                                // Otherwise return to stacked position
                                                swipeableState.animateTo(0)
                                            }
                                        }
                                    }
                                ) { change, dragAmount ->
                                    val newOffset = swipeableState.offset.value + dragAmount
                                    swipeableState.performDrag(newOffset)
                                }
                            }
                            .swipeable(
                                orientation = Orientation.Vertical,
                                state = swipeableState,
                                anchors = anchors,
                                thresholds = { _, _ -> FractionalThreshold(0.5f) }
                            )
                    } else {
                        Modifier
                    }),
                elevation = CardDefaults.cardElevation ((cardCount.lastIndex - index).dp),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color.Black),
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.card_mx_tdc),
                    contentDescription = "Card $index",
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardStackPreview() {
    CardStack()
}