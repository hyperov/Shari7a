package com.nabil.ahmed.shari7a.ui.screens.forecast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale
import com.nabil.ahmed.shari7a.data.local.SettingsManager
import com.nabil.ahmed.shari7a.data.model.MeterType
import com.nabil.ahmed.shari7a.di.settingsDataStore
import com.nabil.ahmed.shari7a.ui.screens.forecast.components.EnergyZoneCard
import com.nabil.ahmed.shari7a.ui.screens.forecast.components.IndicatorLabel
import com.nabil.ahmed.shari7a.ui.screens.forecast.components.MultiSegmentProgressBar
import com.nabil.ahmed.shari7a.ui.theme.Shari7aTheme
import com.nabil.ahmed.shari7a.ui.viewmodel.MainViewModel

@Composable
fun ForecastScreen(viewModel: MainViewModel) {
    val billResult by viewModel.billResult.collectAsState()
    val meterType by viewModel.meterType.collectAsState()
    val actualKwh = billResult?.kwh ?: 0.0
    
    // Local simulation state initialized from actual kwh
    var simulationKwh by remember(actualKwh) { mutableDoubleStateOf(actualKwh) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.End
    ) {
        val headerText = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Black)) {
                append("نواة ")
            }
            withStyle(style = SpanStyle(color = Color(0xFFD81B60))) {
                append("النظام")
            }
        }

        Text(
            text = headerText,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(16.dp))

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

        // Consumption Overview Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "استهلاك الطاقة",
                    color = Color(0xFFD81B60),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineLarge
                )
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "كيلوواط ساعة",
                        fontSize = 18.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding( end = 8.dp)
                    )
                    Text(
                        text = simulationKwh.toInt().toString(),
                        fontSize = 72.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                
                val simulatedResult = remember(simulationKwh, meterType) {
                    com.nabil.ahmed.shari7a.logic.BillCalculator.calculate(simulationKwh, meterType)
                }

                Surface(
                    color = Color(0xFF00BFA5).copy(alpha = 0.3f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color(0xFF000000), fontWeight = FontWeight.Bold)) {
                                append("التكلفة المتوقعة: ")
                            }
                            withStyle(style = SpanStyle(color = Color(0xFFD81B60), fontWeight = FontWeight.Black)) {
                                append(String.format(Locale.US, "%.2f", simulatedResult.totalCost))
                            }
                            withStyle(style = SpanStyle(color = Color(0xFF00BFA5))) {
                                append(" ج.م")
                            }
                        },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Multi-segment progress bar
                MultiSegmentProgressBar(
                    currentKwh = simulationKwh,
                    onKwhChange = { newValue ->
                        simulationKwh = newValue
                    },
                    meterType = meterType
                )
                
                if (meterType == MeterType.LEGAL) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IndicatorLabel("100", Color(0xFF00BFA5))
                        IndicatorLabel("650", Color(0xFFFBC02D))
                        IndicatorLabel("1000", Color(0xFFF4511E))
                        IndicatorLabel("2000", Color(0xFFD81B60))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (meterType == MeterType.LEGAL) {
            Text(
                text = "ملف الطاقة",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Black,
                color = Color(0xFFD81B60)
            )
            Text(
                text = "استهلاك الشبكة وتفاصيل التعريفة في الوقت الفعلي",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF00BFA5),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            EnergyZoneCard(
                "منطقة الدعم",
                "0-100",
                "يتم تطبيق دعم \"الاستهلاك المنخفض\". علامة الخروج عند 100 كيلوواط ساعة.",
                Color(0xFF00BFA5),
                true
            )
            EnergyZoneCard(
                "عادي",
                "101-650",
                "تحذير برتقالي رئيسي عند 650 كيلوواط ساعة. فترة التعرفة وشيكة.",
                Color(0xFF4DB6AC),
                false
            )
            EnergyZoneCard(
                "تعريفة عالية",
                "651-1000",
                "تحذير أحمر عند 1000 كيلوواط ساعة. الانتقال إلى معدل 223 نقطة.",
                Color(0xFFFBC02D),
                false
            )
            EnergyZoneCard(
                "حرج",
                "1001-2000",
                "أقصى خطر عند 2000 كيلوواط ساعة. الانتقال إلى معدل 258 نقطة.",
                Color(0xFFF4511E),
                false
            )
        } else {
            Text(
                text = "نظام التعريفة الموحدة",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Black,
                color = Color(0xFFD81B60)
            )
            Text(
                text = "التفاصيل الخاصة بالعداد الكودي",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF00BFA5),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            EnergyZoneCard(
                "تعريفة موحدة",
                "٢.٧٤",
                "يتم تطبيق سعر ثابت ٢.٧٤ جنيه لكل كيلوواط ساعة بغض النظر عن كمية الاستهلاك. لا توجد شرائح تصاعدية في هذا النظام.",
                Color(0xFF6200EE),
                true
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastScreenPreview() {
    Shari7aTheme {
        val context = LocalContext.current
        val settingsManager = SettingsManager(context.settingsDataStore)
        val viewModel = MainViewModel(settingsManager)
        ForecastScreen(viewModel)
    }
}
