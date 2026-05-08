package com.nabil.ahmed.shari7a.ui.screens.tariff.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nabil.ahmed.shari7a.ui.theme.Shari7aTheme

@Composable
fun TariffTableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEDE7F6))
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HeaderText("الشريحة", Modifier.weight(1.5f))
        HeaderText("النطاق (ك.و.س)", Modifier.weight(3f))
        HeaderText("سعر الوحدة (قرش)", Modifier.weight(3f))
        HeaderText("مقابل خدمة", Modifier.weight(2.5f))
    }
}

@Preview(showBackground = true)
@Composable
fun TariffTableHeaderPreview() {
    Shari7aTheme {
        TariffTableHeader()
    }
}
