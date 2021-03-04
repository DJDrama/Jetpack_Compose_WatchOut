package com.example.androiddevchallenge.ui

import android.util.Log
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
    val listState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()
    val position = remember { mutableStateOf(0) }
    Log.e(
        "asdf",
        "asdfasdfads " + position.value + " " + listState.firstVisibleItemIndex
    )

    Box(modifier = Modifier.height(86.dp).width(56.dp)) {
        Divider(
            color = Color.Blue,
            modifier = Modifier.align(Alignment.Center).padding(bottom = 22.dp)
        )
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.align(Alignment.Center)
        ) {
            itemsIndexed(items = numbers) { index, item ->
                position.value = item
                Text(
                    text = String.format("%2d", item),
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

        coroutineScope.launch {
//            Log.e("fuck", "check : "  +listState.layoutInfo.visibleItemsInfo[0].index
//            + " " +listState.layoutInfo.visibleItemsInfo[1].index + " " +listState.layoutInfo.visibleItemsInfo[2].index)
            //  Log.e("fuck", "Fuck : " + listState.firstVisibleItemIndex)
//            listState.scrollToItem(
//                position.value-2
//            )
        }
        Divider(
            color = Color.Blue,
            modifier = Modifier.align(Alignment.Center).padding(top = 22.dp)
        )
    }

}
