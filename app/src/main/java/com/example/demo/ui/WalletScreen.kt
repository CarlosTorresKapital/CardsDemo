package com.example.demo.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import kotlinx.coroutines.delay

@Composable
fun WalletScreen() {
    // Estado de las tarjetas
    var cards by remember { mutableStateOf(listOf(1, 2, 3, 4, 5)) }

    // ID de la tarjeta seleccionada (nulo si ninguna está seleccionada)
    var selectedCardId by remember { mutableStateOf<Int?>(null) }

    val context = LocalContext.current

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        val screenHeight = maxHeight
        val screenHeightPx = with(LocalDensity.current) { maxHeight.toPx() }

        cards.forEachIndexed { index, _ ->

            val inverseIndex = cards.size - 1 - index
            val inverseSelectedIndex = cards.size - 1 - (selectedCardId ?: 0)

            // offset apilado tipo Wallet
            val cardOffset by animateDpAsState(
                targetValue = when {
                    selectedCardId == null -> (index * 20).dp
                    selectedCardId == index -> 0.dp
                    else -> {
                        val rest =
                            if (inverseIndex < inverseSelectedIndex) inverseIndex + 1 else inverseIndex
                        val totalOffset = screenHeight
                        totalOffset - (rest * 10).dp
                    }
                },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )

            // animación de entrada desde abajo
            val entryAnim = remember { Animatable(screenHeightPx) }
            LaunchedEffect(Unit) {
                entryAnim.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = 600,
                        easing = FastOutSlowInEasing
                    )
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    // entrada inicial
                    .graphicsLayer {
                        translationY = entryAnim.value
                    }
                    // apilado dinámico
                    .offset(y = cardOffset)
                    .clickable {
                        if (selectedCardId == index) {
                            Toast.makeText(context, "NavToDetail", Toast.LENGTH_SHORT).show()
                        } else if (selectedCardId != null) {
                            selectedCardId = null
                        } else {
                            selectedCardId = index
                        }
                    },
                border = BorderStroke(1.dp, Color.Black),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Hola: $index",
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            }
        }
    }
}
