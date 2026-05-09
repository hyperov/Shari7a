package com.nabil.ahmed.shari7a.ui.screens.forecast.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.nabil.ahmed.shari7a.data.model.TariffData
import com.nabil.ahmed.shari7a.ui.theme.Shari7aTheme

@Composable
fun MultiSegmentProgressBar(
    currentKwh: Double,
    onKwhChange: (Double) -> Unit,
    modifier: Modifier = Modifier,
    maxKwh: Double = 2000.0
) {
    var width by remember { mutableIntStateOf(0) }
    
    val animatedProgress by animateFloatAsState(
        targetValue = (currentKwh / maxKwh).toFloat().coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 500),
        label = "ProgressBarAnimation"
    )

    val updatedOnKwhChange by rememberUpdatedState(onKwhChange)
    val latestKwh by rememberUpdatedState(currentKwh)
    val layoutDirection = LocalLayoutDirection.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .onSizeChanged { width = it.width }
            .pointerInput(width) {
                detectTapGestures { offset ->
                    if (width > 0) {
                        val isRtl = layoutDirection == LayoutDirection.Rtl
                        val adjustedX = if (isRtl) width - offset.x else offset.x
                        val newProgress = (adjustedX / width).toDouble().coerceIn(0.0, 1.0)
                        updatedOnKwhChange(newProgress * maxKwh)
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        // Background track with segments
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(CircleShape)
                .background(Color(0xFFEEEEEE))
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                TariffData.tiers.forEach { tier ->
                    val weight = if (tier.maxKwh != null) {
                        (tier.maxKwh.toFloat() - tier.minKwh.toFloat()) / maxKwh.toFloat()
                    } else {
                        (maxKwh.toFloat() - tier.minKwh.toFloat()).coerceAtLeast(0f) / maxKwh.toFloat()
                    }
                    
                    if (weight > 0) {
                        Box(
                            modifier = Modifier
                                .weight(weight)
                                .fillMaxHeight()
                                .background(tier.color.copy(alpha = 0.8f))
                        )
                    }
                }
            }
        }

        // Animated Thumb and Drag Layer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = BiasAlignment(
                horizontalBias = animatedProgress * 2f - 1f,
                verticalBias = 0f
            )
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .pointerInput(width) { // Keyed by width, stable during gesture
                        detectDragGestures(
                            onDrag = { change, dragAmount ->
                                if (width > 0) {
                                    change.consume()
                                    val isRtl = layoutDirection == LayoutDirection.Rtl
                                    val actualDragX = if (isRtl) -dragAmount.x else dragAmount.x
                                    val deltaProgress = actualDragX / width
                                    val newKwh = (latestKwh + (deltaProgress * maxKwh)).coerceIn(0.0, maxKwh)
                                    updatedOnKwhChange(newKwh)
                                }
                            }
                        )
                    }
                    .pointerInput(width) { // Second detector for long press
                        detectDragGesturesAfterLongPress(
                            onDrag = { change, dragAmount ->
                                if (width > 0) {
                                    change.consume()
                                    val isRtl = layoutDirection == LayoutDirection.Rtl
                                    val actualDragX = if (isRtl) -dragAmount.x else dragAmount.x
                                    val deltaProgress = actualDragX / width
                                    val newKwh = (latestKwh + (deltaProgress * maxKwh)).coerceIn(0.0, maxKwh)
                                    updatedOnKwhChange(newKwh)
                                }
                            }
                        )
                    }
                    .clip(CircleShape)
                    .background(Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(Color(0xFFD81B60))
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MultiSegmentProgressBarPreview() {
    var kwh by remember { mutableStateOf(750.0) }
    Shari7aTheme {
        Column(modifier = Modifier.padding(20.dp)) {
            MultiSegmentProgressBar(
                currentKwh = kwh,
                onKwhChange = { kwh = it }
            )
        }
    }
}
