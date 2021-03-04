package com.example.androiddevchallenge.ui

import android.graphics.Color.alpha
import android.graphics.fonts.FontStyle
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@Composable
fun NumberPickerList(numbers: List<Int>) {
    val listState = rememberLazyListState(0, 0)
    val coroutineScope = rememberCoroutineScope()

    val isScrollInProgress = remember { derivedStateOf { listState.isScrollInProgress } }
    val firstVisibleItemIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val firstVisibleItemScrollOffset =
        remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }


    val textSizeState = remember { mutableStateOf(22.sp)}

    Box(modifier = Modifier.width(40.dp).height(96.dp)) {
        LazyColumn(
            state = listState
        ) {
            itemsIndexed(items = numbers) { index, item ->
                if (index == 0 || index == numbers.size - 1) {
                    Spacer(
                        modifier = Modifier.fillMaxWidth().height(32.dp),
                    )
                } else {
                    if (firstVisibleItemIndex.value != item) {
                        textSizeState.value = 18.sp
                    }else{
                        textSizeState.value = 22.sp
                    }
                    Text(
                        text = String.format("%2d", item),
                        fontSize = textSizeState.value,
                        modifier = Modifier.height(32.dp).fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        if (!isScrollInProgress.value) {
            coroutineScope.launch {
                // 85
                if (firstVisibleItemScrollOffset.value < 85 / 2) {
                    listState.animateScrollToItem(firstVisibleItemIndex.value)
                }
                else {
                    listState.animateScrollToItem(firstVisibleItemIndex.value + 1)
                }
            }
        }

    }
}
