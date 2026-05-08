package com.nabil.ahmed.shari7a.ui.screens.tariff

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.nabil.ahmed.shari7a.data.model.TariffData
import com.nabil.ahmed.shari7a.ui.components.TopLogo
import com.nabil.ahmed.shari7a.ui.screens.tariff.components.ClosedTierRow
import com.nabil.ahmed.shari7a.ui.screens.tariff.components.TariffRow
import com.nabil.ahmed.shari7a.ui.screens.tariff.components.TariffTableHeader
import com.nabil.ahmed.shari7a.ui.theme.Shari7aTheme

@Composable
fun TariffScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        TopLogo()
        
        Text(
            text = "التعرفة",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Text(
            text = "بما في ذلك أسعار الشرائح ونطاقات الاستهلاك ورسوم الخدمة. يتبع نموذج التسعير مقياساً تصاعدياً يعتمد على إجمالي الاستهلاك بالكيلوواط ساعة.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Surface(
                color = Color(0xFFE1F5FE),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "الجدول الرئيسي لتقدير ٢٠٢٤ الموحد",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF0277BD)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(Color(0xFF00BFA5))
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column {
                TariffTableHeader()
                HorizontalDivider(thickness = 0.5.dp, color = Color(0xFFEEEEEE))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(TariffData.tiers) { tier ->
                        TariffRow(tier)
                        HorizontalDivider(thickness = 0.5.dp, color = Color(0xFFEEEEEE))
                    }
                    item {
                        ClosedTierRow()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, locale = "ar")
@Composable
fun TariffScreenPreview() {
    Shari7aTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            TariffScreen()
        }
    }
}
