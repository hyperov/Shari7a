package com.nabil.ahmed.shari7a.ui.screens.input.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nabil.ahmed.shari7a.logic.BillResult
import com.nabil.ahmed.shari7a.data.model.TariffTier
import com.nabil.ahmed.shari7a.ui.theme.Shari7aTheme

@Composable
fun ConsumptionIndicator(billResult: BillResult?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = Color(0xFF90003D).copy(alpha = 0.3f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "سكني",
                        color = Color(0xFFD81B60),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Text(
                    text = "الحالة النشطة",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }

            Text(
                text = "الشريحة ${billResult?.tier?.name ?: "-"}",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val currentKwh = billResult?.kwh ?: 0.0
                val maxKwh = billResult?.tier?.maxKwh ?: 2000
                Text(
                    text = "${currentKwh.toInt()} / $maxKwh kWh",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "تقدم الاستهلاك",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            val progress = if (billResult != null) {
                (billResult.kwh / (billResult.tier.maxKwh ?: 2000)).toFloat().coerceIn(0f, 1f)
            } else 0f

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp)),
                color = Color(0xFF00695C),
                trackColor = Color(0xFFE0F2F1)
            )

            Spacer(modifier = Modifier.height(12.dp))
            if (billResult != null && billResult.tier.id < 8)
                Text(
                    text = "الشريحة التالية: الشريحة ${billResult.tier.id + 1} تبدأ قريباً.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConsumptionIndicatorPreview() {
    Shari7aTheme {
        ConsumptionIndicator(
            billResult = BillResult(
                kwh = 120.0,
                consumptionCost = 114.0,
                serviceFee = 6.0,
                totalCost = 120.0,
                tier = TariffTier(
                    id = 3,
                    name = "٣",
                    range = "٠ - ٢٠٠",
                    minKwh = 0,
                    maxKwh = 200,
                    pricePerKwhPiaster = 95.0,
                    serviceFeeEgp = 6.0,
                    color = Color(0xFFFBC02D)
                )
            )
        )
    }
}
