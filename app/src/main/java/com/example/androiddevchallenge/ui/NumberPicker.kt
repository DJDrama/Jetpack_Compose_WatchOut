package com.example.androiddevchallenge.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FloatExponentialDecaySpec
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun NumberPicker(
    state: MutableState<Int>,
    modifier: Modifier = Modifier,
    range: IntRange? = null,
    textStyle: TextStyle = LocalTextStyle.current,
) {
    val coroutineScope = rememberCoroutineScope()
    val numbersColumnHeight = 36.dp
    val halvedNumbersColumnHeight = numbersColumnHeight / 2
    val halvedNumbersColumnHeightPx = with(LocalDensity.current) {
        halvedNumbersColumnHeight.toPx()
    }

    fun getAnimStateValue(offset: Float): Int =
        state.value - (offset / halvedNumbersColumnHeightPx).toInt()

    val animatedOffset = remember { Animatable(0f) }
    range?.let {
        val offsetRange = remember(state.value, range) {
            val value = state.value
            val first = -(range.last - value) * halvedNumbersColumnHeightPx
            val last = -(range.first - value) * halvedNumbersColumnHeightPx
            first..last
        }
        animatedOffset.updateBounds(offsetRange.start, offsetRange.endInclusive)
    }

    val coercedAnimatedOffset = animatedOffset.value % halvedNumbersColumnHeightPx
    val animatedStateValue = getAnimStateValue(animatedOffset.value)

    Column(
        modifier = Modifier.wrapContentSize()
            .draggable(
                orientation = Orientation.Vertical,
                onDragStopped = { velocity->
                    coroutineScope.launch {
                        //state.fling
                    }

                },
                state = rememberDraggableState { dy ->
                    coroutineScope.launch {
                        animatedOffset.snapTo(animatedOffset.value + dy)
                    }
                },

            )
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "${animatedStateValue - 1}",
                modifier = Modifier.align(Alignment.Center)
                    .offset(y = -halvedNumbersColumnHeight)
            )
            Text(
                text = "$animatedStateValue",
                modifier = Modifier.align(Alignment.Center)
            )
            Text(
                text = "${animatedStateValue + 1}",
                        modifier = Modifier.align(Alignment.Center)
                    .offset(y = halvedNumbersColumnHeight)
            )
        }
    }
}



