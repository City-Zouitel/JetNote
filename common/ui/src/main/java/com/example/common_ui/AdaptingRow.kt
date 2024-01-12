package com.example.common_ui

import android.icu.text.MeasureFormat
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.AdaptingRow(
   modifier: Modifier = Modifier,
   content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content.invoke()
    }
}

@Composable
fun AdaptingRowBetween(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content.invoke()
    }
}
