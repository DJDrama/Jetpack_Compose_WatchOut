package com.example.androiddevchallenge.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp

@Composable
fun CountDownTimerScreen() {

    Column(modifier = Modifier.padding(24.dp)) {
        Spacer(modifier = Modifier.height(24.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier.fillMaxWidth().height(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray)
                    .align(Alignment.Center),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Row {
                    NumberPickerList(numbers = (-1..25).toList())
                    Text(text = "h", modifier = Modifier.align(Alignment.CenterVertically))
                }

                Row {
                    NumberPickerList(numbers = (-1..25).toList())
                    Text(text = "m", modifier = Modifier.align(Alignment.CenterVertically))
                }
                Row {
                    NumberPickerList(numbers = (-1..61).toList())
                    Text(text = "s", modifier = Modifier.align(Alignment.CenterVertically))
                }

            }
        }

    }
}