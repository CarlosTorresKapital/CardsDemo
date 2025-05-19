package com.example.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.demo.ui.CardStack
import com.example.demo.ui.CleanDrag
import com.example.demo.ui.theme.PrimaryBlack
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DragScreen() {

    val width = 96.dp
    val squareSize = 48.dp

    val swipeState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1) // Puntos de anclaje (px) -> estados

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .width(width)
                    .background(Color.LightGray)
            ) {
                Box(
                    modifier = Modifier
                        .swipeable(
                            state = swipeState,
                            anchors = anchors,
                            thresholds = { _, _ -> FractionalThreshold(0.5f) },
                            orientation = Orientation.Horizontal
                        )
                        .offset {
                            IntOffset(swipeState.offset.value.roundToInt(), 0)
                        }
                        .size(squareSize)
                        .background(PrimaryBlack)
                )
            }

            CleanDrag(
                modifier = Modifier
                    .background(Color.Cyan)
            )
        }
    }
}



