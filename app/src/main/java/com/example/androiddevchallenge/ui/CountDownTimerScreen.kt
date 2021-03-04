/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarm
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.ui.theme.RunningState
import com.example.androiddevchallenge.utils.formatTime

@Composable
fun CountDownTimerScreen(isDarkTheme: MutableState<Boolean>) {
    val viewModel: CountDownTimerViewModel = viewModel()
    val isCountDownTimerVisible = viewModel.isRunning
    val addedTime = viewModel.addTime(System.currentTimeMillis())
    val dialogState = remember { mutableStateOf(false) }

    if (dialogState.value) {
        ShowDialog {
            dialogState.value = false
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(24.dp)
    ) {
        IconButton(
            onClick = {
                isDarkTheme.value = !isDarkTheme.value
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(
                imageVector = Icons.Default.RemoveRedEye,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        SelectTime(runningState = isCountDownTimerVisible, viewModel = viewModel)
        CircularCountDownTimer(
            runningState = isCountDownTimerVisible,
            viewModel = viewModel,
            addedTime = addedTime,
            dialogState = dialogState
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            Button(
                onClick = {
                    when (viewModel.isRunning) {
                        RunningState.STOPPED -> viewModel.startCountDownTimer()
                        RunningState.STARTED -> viewModel.stopCountDownTimer()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(48.dp).clip(RoundedCornerShape(16.dp))

            ) {
                Text(
                    text = if (viewModel.isRunning == RunningState.STOPPED)
                        "Start"
                    else
                        "Stop"
                )
            }
        }
    }
}

@Composable
fun SelectTime(
    runningState: RunningState,
    viewModel: CountDownTimerViewModel
) {
    if (runningState == RunningState.STOPPED) {
        Box(
            modifier = Modifier.fillMaxWidth().height(350.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().height(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray)
                    .align(Alignment.Center),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row {
                    NumberPickerList(numbers = (-1..25).toList()) {
                        viewModel.hour = it
                    }

                    Text(text = "h", modifier = Modifier.align(Alignment.CenterVertically))
                }

                Row {
                    NumberPickerList(numbers = (-1..61).toList()) {
                        viewModel.minute = it
                    }

                    Text(text = "m", modifier = Modifier.align(Alignment.CenterVertically))
                }
                Row {
                    NumberPickerList(numbers = (-1..61).toList()) {
                        viewModel.second = it
                    }

                    Text(text = "s", modifier = Modifier.align(Alignment.CenterVertically))
                }
            }
        }
    }
}

@Composable
fun CircularCountDownTimer(
    runningState: RunningState,
    viewModel: CountDownTimerViewModel,
    dialogState: MutableState<Boolean>,
    addedTime: String
) {
    if (runningState != RunningState.STOPPED) {
        val leftTime = viewModel.leftTime.value
        if (leftTime == 0) {
            dialogState.value = true
            viewModel.stopCountDownTimer()
        }
        val progress =
            remember { Animatable(leftTime / viewModel.getTotalTimeInSeconds().toFloat()) }
        val progressTarget = 0f

        LaunchedEffect(runningState == RunningState.STARTED) {
            progress.animateTo(
                targetValue = progressTarget,
                animationSpec = tween(
                    durationMillis = leftTime * 1000,
                    easing = LinearEasing
                )
            )
        }
        Box(
            modifier = Modifier.size(350.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                color = Color.LightGray,
                progress = 100f,
                strokeWidth = 10.dp
            )

            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = progress.value,
                strokeWidth = 10.dp
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${formatTime(isLeadingZeroNeeded = true, value = leftTime / 3600)}:" +
                        "${
                        formatTime(isLeadingZeroNeeded = true, value = (leftTime / 60) % 60)
                        }:" +
                        formatTime(isLeadingZeroNeeded = true, value = leftTime % 60),
                    fontSize = 48.sp

                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccessAlarm,
                        modifier = Modifier.padding(4.dp),
                        contentDescription = null
                    )
                    Text(
                        text = addedTime
                    )
                }
            }
        }
    }
}

@Composable
fun ShowDialog(onDismiss: () -> Unit) {
    AlertDialog(
        text = {
            Text(
                text = "Watch Out!",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.h5
            )
        },
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "Ok")
                }
            }
        },
        onDismissRequest = onDismiss
    )
}
