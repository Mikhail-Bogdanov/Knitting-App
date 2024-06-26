package com.evoteam.presentation.ui.composables.presentation.start.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.evoteam.presentation.ui.composables.presentation.start.mvi.StartEvent
import com.evoteam.presentation.ui.composables.presentation.start.mvi.StartEvent.AddLoopClicked
import com.evoteam.presentation.ui.composables.presentation.start.mvi.StartEvent.AddRowClicked
import com.evoteam.presentation.ui.composables.presentation.start.mvi.StartEvent.LoopClicked
import com.evoteam.presentation.ui.composables.presentation.start.mvi.StartEvent.RemoveLoopClicked
import com.evoteam.presentation.ui.composables.presentation.start.mvi.StartState
import com.evoteam.presentation.ui.utils.composables.ComposableConstants
import com.evoteam.presentation.ui.utils.composables.HorizontalScrollBox

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StartStamp(
    state: StartState,
    onEvent: (StartEvent) -> Unit
) = HorizontalScrollBox(
    alignment = Alignment.TopStart
) {
    LazyColumn(
        modifier = Modifier
            .defaultMinSize(minWidth = ComposableConstants.ScreenWidth)
            .fillMaxHeight(0.7f),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(
            count = state.loops.size,
            key = { it }
        ) { rowIndex ->
            val reversedRowIndex = state.loops.size.minus(1).minus(rowIndex)

            if (rowIndex == 0) StartAddItem(
                modifier = Modifier
                    .padding(bottom = 2.dp)
            ) {
                onEvent(AddRowClicked)
            }

            Row(
                modifier = Modifier.animateItemPlacement(),
                horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ) {
                state.loops[reversedRowIndex].forEach { loop ->
                    StartLoopItem(
                        loop = loop,
                        onLoopClick = {
                            onEvent(LoopClicked(loop))
                        },
                        onRemoveClick = {
                            onEvent(RemoveLoopClicked(loop))
                        }
                    )
                }

                StartAddItem {
                    onEvent(AddLoopClicked(reversedRowIndex))
                }
            }
        }
    }
}