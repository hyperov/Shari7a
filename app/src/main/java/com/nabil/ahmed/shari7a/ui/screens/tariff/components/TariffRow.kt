package com.nabil.ahmed.shari7a.ui.screens.tariff.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nabil.ahmed.shari7a.data.model.TariffTier
import com.nabil.ahmed.shari7a.ui.theme.Shari7aTheme

@Composable
fun TariffRow(tier: TariffTier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1.5f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = tier.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(tier.color)
            )
        }
        
        Text(
            text = tier.range,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(3f)
        )
        
        Text(
            text = "${tier.pricePerKwhPiaster.toInt()} قرش",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00BFA5),
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(3f)
        )
        
        Text(
            text = "${tier.serviceFeeEgp.toInt()} ج.م",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(2.5f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TariffRowPreview() {
    Shari7aTheme {
        TariffRow(
            tier = TariffTier(
                id = 1,
                name = "الأولى",
                range = "0 - 50",
                minKwh = 0,
                maxKwh = 50,
                pricePerKwhPiaster = 68.0,
                serviceFeeEgp = 1.0,
                color = Color.Green
            )
        )
    }
}
