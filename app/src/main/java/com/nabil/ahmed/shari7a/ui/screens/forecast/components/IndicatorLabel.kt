package com.nabil.ahmed.shari7a.ui.screens.forecast.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nabil.ahmed.shari7a.ui.theme.Shari7aTheme

@Composable
fun IndicatorLabel(text: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(2.dp, 8.dp).background(Color.LightGray))
        Text(text = text, style = MaterialTheme.typography.labelSmall, color = color, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun IndicatorLabelPreview() {
    Shari7aTheme {
        IndicatorLabel("100", Color.Green)
    }
}
