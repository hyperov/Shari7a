package com.nabil.ahmed.shari7a.ui.screens.forecast.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.nabil.ahmed.shari7a.ui.theme.Shari7aTheme

@Composable
fun RowScope.Segment(weight: Float, color: Color) {
    Box(modifier = Modifier.weight(weight).fillMaxHeight().background(color))
}

@Preview(showBackground = true)
@Composable
fun SegmentPreview() {
    Shari7aTheme {
        Row {
            Segment(1f, Color.Red)
        }
    }
}
