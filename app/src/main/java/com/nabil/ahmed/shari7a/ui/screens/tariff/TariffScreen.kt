package com.nabil.ahmed.shari7a.ui.screens.tariff

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nabil.ahmed.shari7a.data.local.SettingsManager
import com.nabil.ahmed.shari7a.data.model.MeterType
import com.nabil.ahmed.shari7a.data.model.TariffData
import com.nabil.ahmed.shari7a.di.settingsDataStore
import com.nabil.ahmed.shari7a.ui.screens.tariff.components.ClosedTierRow
import com.nabil.ahmed.shari7a.ui.screens.tariff.components.TariffRow
import com.nabil.ahmed.shari7a.ui.screens.tariff.components.TariffTableHeader
import com.nabil.ahmed.shari7a.ui.theme.Shari7aTheme
import com.nabil.ahmed.shari7a.ui.viewmodel.MainViewModel

@Composable
fun TariffScreen(viewModel: MainViewModel) {
    val meterType by viewModel.meterType.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFBAFFE5))
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text( modifier = Modifier
            .fillMaxWidth(),
            text = "جدول الشرائح",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center

        )

        Text(
            text = if (meterType == MeterType.LEGAL)
                "بما في ذلك أسعار الشرائح ونطاقات الاستهلاك ورسوم الخدمة. يتبع نموذج التسعير مقياساً تصاعدياً يعتمد على إجمالي الاستهلاك بالكيلوواط ساعة."
            else
                "العداد الكودي يعتمد على سعر موحد لجميع مستويات الاستهلاك، مما يسهل حساب التكلفة الشهرية بشكل ثابت.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Meter Type Selector
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            color = Color.White.copy(alpha = 0.5f),
            shape = RoundedCornerShape(12.dp)
        ) {
            TabRow(
                selectedTabIndex = if (meterType == MeterType.LEGAL) 0 else 1,
                containerColor = Color.Transparent,
                contentColor = Color(0xFFD81B60),
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[if (meterType == MeterType.LEGAL) 0 else 1]),
                        color = Color(0xFFD81B60)
                    )
                },
                divider = {}
            ) {
                Tab(
                    selected = meterType == MeterType.LEGAL,
                    onClick = { viewModel.setMeterType(MeterType.LEGAL) },
                    text = { Text("قانوني", fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = meterType == MeterType.CODE,
                    onClick = { viewModel.setMeterType(MeterType.CODE) },
                    text = { Text("كودي", fontWeight = FontWeight.Bold) }
                )
            }
        }

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
            if (meterType == MeterType.LEGAL) {
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
            } else {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "التعرفة الموحدة",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6200EE)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "2.74",
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFFD81B60)
                    )
                    Text(
                        text = "جنيه لكل كيلوواط ساعة",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider(color = Color(0xFFEEEEEE))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "يتم تطبيق هذا السعر على كافة مستويات الاستهلاك دون الانتقال بين شرائح مختلفة.",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, locale = "ar")
@Composable
fun TariffScreenPreview() {
    Shari7aTheme {
        val context = LocalContext.current
        val settingsManager = SettingsManager(context.settingsDataStore)
        val viewModel = MainViewModel(settingsManager)
        TariffScreen(viewModel)
    }
}
