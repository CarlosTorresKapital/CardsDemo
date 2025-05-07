package com.example.demo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.skydoves.cloudy.cloudy

@Composable
fun KapitalCard(
    modifier: Modifier = Modifier,
    type: Int,
    cardNumber: String,
    isHidden: Boolean = false
) {

    var animateNumbers by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        animateNumbers = true
    }

    Card(
        modifier = modifier
            .height(196.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            ConstraintLayout(
                modifier = modifier
                    .height(196.dp)
                    .cloudy(
                        enabled = isHidden,
                        radius = 20
                    )
            ) {

                val (bgLogo, lastDigits, dots, anim) = createRefs()

                Image(
                    modifier = Modifier
                        .constrainAs(bgLogo) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            height = Dimension.fillToConstraints
                            width = Dimension.fillToConstraints
                        },
                    contentScale = ContentScale.FillBounds,
                    painter = painterResource(typeToResource(type)),
                    contentDescription = "background logo"
                )

                if (!isHidden) {

                    Image(
                        modifier = Modifier
                            .constrainAs(dots) {
                                start.linkTo(parent.start, margin = 16.dp)
                                bottom.linkTo(parent.bottom, margin = 20.dp)
                                height = Dimension.value(6.dp)
                                width = Dimension.value(36.dp)
                            },
                        painter = painterResource(R.drawable.block),
                        contentDescription = "cardDots"
                    )

                    Text(
                        modifier = Modifier
                            .constrainAs(lastDigits) {
                                bottom.linkTo(parent.bottom, margin = 16.dp)
                                start.linkTo(dots.end, margin = 8.dp)
                                height = Dimension.value(18.dp)
                            },
                        textAlign = TextAlign.Start,
                        text = cardNumber.toString(),
                        fontSize = 16.sp,
                        color = androidx.compose.ui.graphics.Color.White,
                        lineHeight = 16.sp
                    )

                }

            }

            if (isHidden) {

                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_locked_card),
                        modifier = Modifier.size(32.dp),
                        contentDescription = "locked card",
                        tint = Color(0xffa3a3a3)
                    )

                    Text(
                        text = "Tarjeta apagada",
                        color = Color(0xffa3a3a3),
                        fontSize = 13.sp,
                        fontWeight = Bold
                    )

                }

            }

        }

    }

}

fun typeToResource(type: Int): Int {
    return when (type) {
        1 -> R.drawable.card_mx_tdc
        2 -> R.drawable.card_world_elite
        else -> R.drawable.card_mx_tdc
    }
}


@Preview
@Composable
private fun CardPrev() {
    KapitalCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        cardNumber = "1234",
        isHidden = true,
        type = 1
    )
}