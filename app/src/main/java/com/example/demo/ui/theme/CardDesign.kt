package com.example.demo.ui.theme

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Text
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.demo.R
import kotlinx.coroutines.launch

@Composable
fun CardDesign(
    containerModifier: Modifier = Modifier,
    scrollableModifier: Modifier = Modifier,
    lateralWidth: Dp,
    isFirstCard: Boolean,
    cardClick: () -> Unit,
){
    Box(
        modifier = containerModifier
    ){

        Card(
            modifier = Modifier
                .fillMaxSize(),
            elevation = 10.dp,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, Color.Black),
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        cardClick()
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
                    text = "Card",
                    color = Color.White
                )
            }

        }

        Box(
            modifier = Modifier
                .width(lateralWidth)
                .fillMaxHeight()
                .align(Alignment.CenterStart)
                .then(
                    if (isFirstCard) {
                        Modifier
                    } else {
                        scrollableModifier
                    }
                )
        )



        Box(
            modifier = Modifier
                .width(lateralWidth)
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
                .then(
                    if (isFirstCard) {
                        Modifier
                    } else {
                        scrollableModifier
                    }
                )
        )

    }
}