package com.nabil.ahmed.shari7a.ui.screens

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nabil.ahmed.shari7a.R
import com.nabil.ahmed.shari7a.data.model.TariffData
import com.nabil.ahmed.shari7a.data.model.TariffTier
import com.nabil.ahmed.shari7a.ui.components.TopLogo
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

@Composable
fun HeaderText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

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

@Composable
fun ClosedTierRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1.5f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "مغلقة",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(4.dp))
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.Gray)
            )
        }
        
        Text(
            text = "(صفر) .",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(3f)
        )
        
        Text(
            text = "—",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(3f)
        )
        
        Text(
            text = "٩ ج.م",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(2.5f)
        )
    }
}
