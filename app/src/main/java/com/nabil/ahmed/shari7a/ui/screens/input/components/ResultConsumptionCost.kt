package com.nabil.ahmed.shari7a.ui.screens.input.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nabil.ahmed.shari7a.logic.BillResult
import com.nabil.ahmed.shari7a.data.model.TariffTier
import com.nabil.ahmed.shari7a.ui.theme.Shari7aTheme
import java.util.Locale

@Composable
fun ResultConsumptionCost(billResult: BillResult?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF00E5FF))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            DetailRow("سعر الشريحة", "${billResult?.tier?.pricePerKwhPiaster ?: 0} kWh")
            DetailRow(
                "رسوم الخدمة",
                "${String.format(Locale.US, "%.2f", billResult?.serviceFee ?: 0.0)} EGP"
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.White.copy(alpha = 0.5f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "التكلفة التقديرية",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF004D40)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "ج.م",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD81B60),
                    modifier = Modifier.padding(bottom = 12.dp, end = 4.dp)
                )
                Text(
                    text = String.format(Locale.US, "%.2f", billResult?.totalCost ?: 0.0),
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFFD81B60)
                )
            }

            Text(
                text = "هذه التكلفة تشمل رسوم الخدمة فقط ولا تشمل الرسوم الإدارية الأخرى",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF004D40).copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultConsumptionCostPreview() {
    Shari7aTheme {
        ResultConsumptionCost(
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
