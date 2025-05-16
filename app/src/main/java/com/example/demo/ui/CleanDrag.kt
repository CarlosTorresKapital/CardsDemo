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
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.demo.R
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Preview(showBackground = true)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CleanDrag(
    modifier: Modifier = Modifier
) {

    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {

        val boxHeightDP = this.maxHeight
        val containerHeightPx = with(LocalDensity.current) {
            boxHeightDP.toPx()
        }

        val cardHeight = 190.dp
        val cardHeightPx = with(LocalDensity.current) {
            cardHeight.toPx()
        }

        val swipeableState = rememberSwipeableState(0)
        val anchors = mapOf(
            0f to 0,
            containerHeightPx - cardHeightPx to 1
        )

        val coroutineScope = rememberCoroutineScope()

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(cardHeight)
                .offset { IntOffset(0, swipeableState.offset.value.roundToInt()) }
                .pointerInput(Unit) {
                    detectVerticalDragGestures(
                        onDragEnd = {
                            coroutineScope.launch {
                                swipeableState.animateTo(swipeableState.currentValue)
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
                ),
            elevation = 10.dp,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, Color.Black),
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(R.drawable.card_mx_tdc),
                contentDescription = "Card",
                contentScale = ContentScale.FillBounds
            )
        }

        Divider(
            modifier = Modifier.align(Alignment.Center)
        )

    }

}