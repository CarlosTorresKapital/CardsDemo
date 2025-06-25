package com.example.demo.ui.login

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.demo.R

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {

    var stepCount by remember { mutableIntStateOf(0) }
    var previousStep by remember { mutableIntStateOf(0) }

    LaunchedEffect(stepCount) {
        previousStep = stepCount
    }

    val isForward = stepCount > previousStep

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Black
    ) {

        Box(
            modifier = Modifier
                .padding(it)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(2f)
                ) {

                    VerticalDivider(
                        modifier = Modifier
                            .padding(vertical = 6.dp)
                            .fillMaxHeight()
                            .width(3.dp)
                            .align(Alignment.Center),
                        color = Color.White
                    )

                    AnimatedContent(
                        modifier = Modifier
                            .fillMaxHeight()
                            .align(Alignment.Center),
                        targetState = stepCount,
                        transitionSpec = {
                            if (isForward) {
                                slideInVertically { height -> height } + fadeIn() togetherWith
                                        slideOutVertically { height -> -height } + fadeOut()
                            } else {
                                slideInVertically { height -> -height } + fadeIn() togetherWith
                                        slideOutVertically { height -> height } + fadeOut()
                            }.using(
                                SizeTransform(clip = false)
                            )
                        },
                        label = ""
                    ) {

                        when (it){

                            0 -> {

                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {

                                    Icon(
                                        painter = painterResource(R.drawable.baseline_circle_24),
                                        contentDescription = "check",
                                        tint = Color.White
                                    )

                                    Spacer(
                                        modifier =
                                            Modifier.weight(1f)
                                    )

                                    Icon(
                                        painter = painterResource(R.drawable.baseline_circle_24),
                                        contentDescription = "check",
                                        tint = Color.Gray
                                    )

                                    Icon(
                                        painter = painterResource(R.drawable.baseline_circle_24),
                                        contentDescription = "check",
                                        tint = Color.Gray
                                    )

                                    Icon(
                                        painter = painterResource(R.drawable.baseline_circle_24),
                                        contentDescription = "check",
                                        tint = Color.Gray
                                    )

                                }

                            }

                            1 -> {

                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {

                                    Icon(
                                        painter = painterResource(R.drawable.baseline_circle_24),
                                        contentDescription = "check",
                                        tint = Color.White
                                    )

                                    Spacer(
                                        modifier =
                                        Modifier.weight(1f)
                                    )

                                    Icon(
                                        painter = painterResource(R.drawable.baseline_circle_24),
                                        contentDescription = "check",
                                        tint = Color.Gray
                                    )

                                    Icon(
                                        painter = painterResource(R.drawable.baseline_circle_24),
                                        contentDescription = "check",
                                        tint = Color.Gray
                                    )

                                }

                            }

                            2 -> {

                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {

                                    Icon(
                                        painter = painterResource(R.drawable.baseline_circle_24),
                                        contentDescription = "check",
                                        tint = Color.White
                                    )

                                    Spacer(
                                        modifier =
                                        Modifier.weight(1f)
                                    )

                                    Icon(
                                        painter = painterResource(R.drawable.baseline_circle_24),
                                        contentDescription = "check",
                                        tint = Color.Gray
                                    )

                                }

                            }

                            3 -> {

                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {

                                    Icon(
                                        painter = painterResource(R.drawable.baseline_circle_24),
                                        contentDescription = "check",
                                        tint = Color.White
                                    )

                                    Spacer(
                                        modifier =
                                        Modifier.weight(1f)
                                    )

                                }

                            }

                            else -> {

                            }

                        }

                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(8f)
                ) {
                    AnimatedContent(
                        modifier = Modifier
                            .fillMaxSize(),
                        targetState = stepCount,
                        transitionSpec = {
                            if (isForward) {
                                slideInVertically { height -> height } + fadeIn() togetherWith
                                        slideOutVertically { height -> -height } + fadeOut()
                            } else {
                                slideInVertically { height -> -height } + fadeIn() togetherWith
                                        slideOutVertically { height -> height } + fadeOut()
                            }.using(
                                SizeTransform(clip = false)
                            )
                        },
                        label = ""
                    ) {

                        when(it){

                            0 -> {

                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {

                                    Spacer(
                                        modifier =
                                        Modifier.weight(1f)
                                    )

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {

                                        Button(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            onClick = {
                                                stepCount += 1
                                            }
                                        ) {

                                            Text("Siguiente")

                                        }

                                    }

                                }

                            }

                            1 -> {

                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {

                                    Spacer(
                                        modifier =
                                        Modifier.weight(1f)
                                    )

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {

                                        Button(
                                            modifier = Modifier
                                                .weight(1f),
                                            onClick = {
                                                stepCount -= 1
                                            }
                                        ) {

                                            Text("Anterior")

                                        }

                                        Button(
                                            modifier = Modifier
                                                .weight(1f),
                                            onClick = {
                                                stepCount += 1
                                            }
                                        ) {

                                            Text("Siguiente")

                                        }

                                    }

                                }

                            }

                            2 -> {

                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {

                                    Spacer(
                                        modifier =
                                        Modifier.weight(1f)
                                    )

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {

                                        Button(
                                            modifier = Modifier
                                                .weight(1f),
                                            onClick = {
                                                stepCount -= 1
                                            }
                                        ) {

                                            Text("Anterior")

                                        }

                                        Button(
                                            modifier = Modifier
                                                .weight(1f),
                                            onClick = {
                                                stepCount += 1
                                            }
                                        ) {

                                            Text("Siguiente")

                                        }

                                    }

                                }

                            }

                            3 -> {

                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {

                                    Spacer(
                                        modifier =
                                        Modifier.weight(1f)
                                    )

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {

                                        Button(
                                            modifier = Modifier
                                                .weight(1f),
                                            onClick = {
                                                stepCount -= 1
                                            }
                                        ) {

                                            Text("Anterior")

                                        }

                                        Button(
                                            modifier = Modifier
                                                .weight(1f),
                                            onClick = {
                                            }
                                        ) {

                                            Text("Terminar")

                                        }

                                    }

                                }

                            }

                            else -> {

                            }

                        }

                    }
                }

            }

        }

    }

}