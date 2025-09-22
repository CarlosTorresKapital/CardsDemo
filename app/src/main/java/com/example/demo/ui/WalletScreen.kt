package com.example.demo.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

@Composable
fun WalletScreen() {
    // Estado de las tarjetas
    var cards by remember {
        mutableStateOf(
            listOf(1,2,3,4,5)
        )
    }

    // ID de la tarjeta seleccionada (nulo si ninguna está seleccionada)
    var selectedCardId by remember { mutableStateOf<Int?>(null) }

    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {

        val screenHeight = maxHeight

        cards.forEachIndexed { index, cardId ->

            val inverseIndex = cards.size - 1 - index
            val inverseSelectedIndex = cards.size - 1 - (selectedCardId ?: 0)

            val cardOffset by animateFloatAsState(
                targetValue = if (selectedCardId == null){
                    with(density) {
                        (index * 20).dp.toPx()
                    }
                } else if (selectedCardId == index){
                    0f
                } else {
                    with(density) {
                        val rest = if (inverseIndex < inverseSelectedIndex) inverseIndex +1 else inverseIndex
                        val totalOffset = (screenHeight/2 - 150.dp).toPx()
                        val offsetCalculated = totalOffset - ( rest * 10 ).dp.toPx()
                        offsetCalculated
                    }
                },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .offset(y = cardOffset.dp)
                    .clickable {
                        selectedCardId = if (selectedCardId != null && selectedCardId == index) {
                                null
                            } else {
                                index

                        }
                    },
                border = BorderStroke(1.dp, Color.Black),
                shape = RoundedCornerShape(16.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()

                ){
                    Text(text = "Hola: $cardId", modifier = Modifier.align(Alignment.TopCenter))
                }

            }

        }


    }
}

@Composable
fun CardItem(card: Card, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .height(200.dp)
            .padding(8.dp)
            .border(1.dp, Color.Black)
            .background(
                color = if (card.isSelected) Color.White else Color.Gray,
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(text = card.title, style = MaterialTheme.typography.bodySmall, color = Color.Black)
    }
}

data class Card(
    val id: Int,
    val title: String,
    val isSelected: Boolean = false
)