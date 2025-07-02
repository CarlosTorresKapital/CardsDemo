package com.example.demo.ui.login

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.demo.R

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {

    var stepCount by remember { mutableIntStateOf(0) }
    var previousStep by remember { mutableIntStateOf(0) }

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    val isEmailContinueButtonEnabled by remember {
        derivedStateOf { email.isNotEmpty() }
    }

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

                //region leftDots
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

                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.baseline_circle_24),
                            contentDescription = "check",
                            tint = Color.White
                        )

                    }

//                    AnimatedContent(
//                        modifier = Modifier
//                            .fillMaxHeight()
//                            .align(Alignment.Center),
//                        targetState = stepCount,
//                        transitionSpec = {
//                            if (isForward) {
//                                slideInVertically { height -> height } + fadeIn() togetherWith
//                                        slideOutVertically { height -> -height } + fadeOut()
//                            } else {
//                                slideInVertically { height -> -height } + fadeIn() togetherWith
//                                        slideOutVertically { height -> height } + fadeOut()
//                            }.using(
//                                SizeTransform(clip = false)
//                            )
//                        },
//                        label = ""
//                    ) {
//
//                        when (it){
//
//                            0 -> {
//
//                                Column(
//                                    modifier = Modifier
//                                        .fillMaxHeight(),
//                                    horizontalAlignment = Alignment.CenterHorizontally,
//                                    verticalArrangement = Arrangement.Center
//                                ) {
//
//                                    Icon(
//                                        painter = painterResource(R.drawable.baseline_circle_24),
//                                        contentDescription = "check",
//                                        tint = Color.White
//                                    )
//
//                                    Spacer(
//                                        modifier =
//                                            Modifier.weight(1f)
//                                    )
//
//                                    Icon(
//                                        painter = painterResource(R.drawable.baseline_circle_24),
//                                        contentDescription = "check",
//                                        tint = Color.Gray
//                                    )
//
//                                    Icon(
//                                        painter = painterResource(R.drawable.baseline_circle_24),
//                                        contentDescription = "check",
//                                        tint = Color.Gray
//                                    )
//
//                                    Icon(
//                                        painter = painterResource(R.drawable.baseline_circle_24),
//                                        contentDescription = "check",
//                                        tint = Color.Gray
//                                    )
//
//                                }
//
//                            }
//
//                            1 -> {
//
//                                Column(
//                                    modifier = Modifier
//                                        .fillMaxHeight(),
//                                    horizontalAlignment = Alignment.CenterHorizontally,
//                                    verticalArrangement = Arrangement.Center
//                                ) {
//
//                                    Icon(
//                                        painter = painterResource(R.drawable.baseline_circle_24),
//                                        contentDescription = "check",
//                                        tint = Color.Gray
//                                    )
//
//                                    Icon(
//                                        painter = painterResource(R.drawable.baseline_circle_24),
//                                        contentDescription = "check",
//                                        tint = Color.White
//                                    )
//
//                                    Spacer(
//                                        modifier =
//                                        Modifier.weight(1f)
//                                    )
//
//                                    Icon(
//                                        painter = painterResource(R.drawable.baseline_circle_24),
//                                        contentDescription = "check",
//                                        tint = Color.Gray
//                                    )
//
//                                    Icon(
//                                        painter = painterResource(R.drawable.baseline_circle_24),
//                                        contentDescription = "check",
//                                        tint = Color.Gray
//                                    )
//
//                                }
//
//                            }
//
//                            2 -> {
//
//                                Column(
//                                    modifier = Modifier
//                                        .fillMaxHeight(),
//                                    horizontalAlignment = Alignment.CenterHorizontally,
//                                    verticalArrangement = Arrangement.Center
//                                ) {
//
//                                    Icon(
//                                        painter = painterResource(R.drawable.baseline_circle_24),
//                                        contentDescription = "check",
//                                        tint = Color.Gray
//                                    )
//
//                                    Icon(
//                                        painter = painterResource(R.drawable.baseline_circle_24),
//                                        contentDescription = "check",
//                                        tint = Color.Gray
//                                    )
//
//                                    Icon(
//                                        painter = painterResource(R.drawable.baseline_circle_24),
//                                        contentDescription = "check",
//                                        tint = Color.White
//                                    )
//
//                                    Spacer(
//                                        modifier =
//                                        Modifier.weight(1f)
//                                    )
//
//                                    Icon(
//                                        painter = painterResource(R.drawable.baseline_circle_24),
//                                        contentDescription = "check",
//                                        tint = Color.Gray
//                                    )
//
//                                }
//
//                            }
//
//                            3 -> {
//
//                                Column(
//                                    modifier = Modifier
//                                        .fillMaxHeight(),
//                                    horizontalAlignment = Alignment.CenterHorizontally,
//                                    verticalArrangement = Arrangement.Center
//                                ) {
//                                    Icon(
//                                        painter = painterResource(R.drawable.baseline_circle_24),
//                                        contentDescription = "check",
//                                        tint = Color.Gray
//                                    )
//                                    Icon(
//                                        painter = painterResource(R.drawable.baseline_circle_24),
//                                        contentDescription = "check",
//                                        tint = Color.Gray
//                                    )
//                                    Icon(
//                                        painter = painterResource(R.drawable.baseline_circle_24),
//                                        contentDescription = "check",
//                                        tint = Color.Gray
//                                    )
//
//                                    Icon(
//                                        painter = painterResource(R.drawable.baseline_circle_24),
//                                        contentDescription = "check",
//                                        tint = Color.White
//                                    )
//
//                                    Spacer(
//                                        modifier =
//                                        Modifier.weight(1f)
//                                    )
//
//                                }
//
//                            }
//
//                            else -> {
//
//                            }
//
//                        }
//
//                    }
                }
                //endregion

                //region rightSteps
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
                    ) { stepCounter ->

                        when(stepCounter){

                            0 -> {

                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .imePadding()
                                        .animateContentSize(),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {

                                    OutlinedTextField(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth(),
                                        value = email,
                                        onValueChange = { newEmail ->
                                            email = newEmail
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        label = {
                                            Text(text = "Correo electrónico")
                                        },
                                        placeholder = {
                                            Text(text = "correo@dominio.com")
                                        },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = Color.White,
                                            unfocusedBorderColor = Color.White,
                                            cursorColor = Color.White,
                                            unfocusedPlaceholderColor = Color.Gray,
                                            unfocusedTextColor = Color.White,
                                            focusedPlaceholderColor = Color.Gray,
                                            focusedTextColor = Color.White,
                                            focusedContainerColor = Color.Black,
                                            unfocusedContainerColor = Color.Black,
                                            focusedLabelColor = Color.White,
                                            unfocusedLabelColor = Color.White,
                                        ),
                                    )

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {

                                        OutlinedButton(
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .fillMaxWidth(),
                                            shape = RoundedCornerShape(16.dp),
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                containerColor = Color.Black,
                                                disabledContainerColor = Color.Gray
                                            ),
                                            border = BorderStroke(
                                                width = 1.dp,
                                                color = Color.White
                                            ),
                                            enabled = isEmailContinueButtonEnabled,
                                            onClick = {
                                                if (email.isNotEmpty()){
                                                    stepCount += 1
                                                }
                                            }
                                        ) {

                                            Text(
                                                "Siguiente",
                                                color = Color.White
                                            )

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

                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        text = email,
                                        color = Color.Gray,
                                        textAlign = TextAlign.Start
                                    )

                                    OutlinedTextField(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth(),
                                        value = password,
                                        onValueChange = { newPassword ->
                                            password = newPassword
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        label = {
                                            Text(text = "Contraseña")
                                        },
                                        placeholder = {
                                            Text(text = "*******")
                                        },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = Color.White,
                                            unfocusedBorderColor = Color.White,
                                            cursorColor = Color.White,
                                            unfocusedPlaceholderColor = Color.Gray,
                                            unfocusedTextColor = Color.White,
                                            focusedPlaceholderColor = Color.Gray,
                                            focusedTextColor = Color.White,
                                            focusedContainerColor = Color.Black,
                                            unfocusedContainerColor = Color.Black,
                                            focusedLabelColor = Color.White,
                                            unfocusedLabelColor = Color.White,
                                        ),
                                    )

                                    Spacer(
                                        modifier =
                                        Modifier.weight(1f)
                                    )

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {

                                        OutlinedButton(
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .weight(1f),
                                            shape = RoundedCornerShape(16.dp),
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                containerColor = Color.Black,
                                            ),
                                            border = BorderStroke(
                                                width = 1.dp,
                                                color = Color.White
                                            ),
                                            onClick = {
                                                stepCount -= 1
                                            }
                                        ) {

                                            Text(
                                                "Anterior",
                                                color = Color.White
                                            )

                                        }

                                        OutlinedButton(
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .weight(1f),
                                            shape = RoundedCornerShape(16.dp),
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                containerColor = Color.Black,
                                            ),
                                            border = BorderStroke(
                                                width = 1.dp,
                                                color = Color.White
                                            ),
                                            onClick = {
                                                if (password.isNotEmpty()){
                                                    stepCount += 1
                                                }
                                            }
                                        ) {

                                            Text(
                                                "Siguiente",
                                                color = Color.White
                                            )

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

                                        OutlinedButton(
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .weight(1f),
                                            shape = RoundedCornerShape(16.dp),
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                containerColor = Color.Black,
                                            ),
                                            border = BorderStroke(
                                                width = 1.dp,
                                                color = Color.White
                                            ),
                                            onClick = {
                                                stepCount -= 1
                                            }
                                        ) {

                                            Text(
                                                "Anterior",
                                                color = Color.White
                                            )

                                        }

                                        OutlinedButton(
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .weight(1f),
                                            shape = RoundedCornerShape(16.dp),
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                containerColor = Color.Black,
                                            ),
                                            border = BorderStroke(
                                                width = 1.dp,
                                                color = Color.White
                                            ),
                                            onClick = {
                                                stepCount += 1
                                            }
                                        ) {

                                            Text(
                                                "Siguiente",
                                                color = Color.White
                                            )

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

                                        OutlinedButton(
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .weight(1f),
                                            shape = RoundedCornerShape(16.dp),
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                containerColor = Color.Black,
                                            ),
                                            border = BorderStroke(
                                                width = 1.dp,
                                                color = Color.White
                                            ),
                                            onClick = {
                                                stepCount -= 1
                                            }
                                        ) {

                                            Text(
                                                "Anterior",
                                                color = Color.White
                                            )

                                        }

                                        OutlinedButton(
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .weight(1f),
                                            shape = RoundedCornerShape(16.dp),
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                containerColor = Color.Black,
                                            ),
                                            border = BorderStroke(
                                                width = 1.dp,
                                                color = Color.White
                                            ),
                                            onClick = {
                                            }
                                        ) {

                                            Text(
                                                "Terminar",
                                                color = Color.White
                                            )

                                        }

                                    }

                                }

                            }

                            else -> {

                            }

                        }

                    }
                }
                //endregion

            }

        }

    }

}