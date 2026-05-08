package com.nabil.ahmed.shari7a.ui.screens.input.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nabil.ahmed.shari7a.ui.theme.Shari7aTheme

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = value, fontWeight = FontWeight.Bold, color = Color(0xFF004D40))
        Text(text = label, color = Color(0xFF004D40).copy(alpha = 0.7f))
    }
}

@Preview(showBackground = true)
@Composable
fun DetailRowPreview() {
    Shari7aTheme {
        DetailRow("الطاقة المستهلكة", "100 kWh")
    }
}
