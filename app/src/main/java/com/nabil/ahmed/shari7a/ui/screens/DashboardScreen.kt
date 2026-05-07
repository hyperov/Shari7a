package com.nabil.ahmed.shari7a.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ElectricBolt
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nabil.ahmed.shari7a.ui.components.TopLogo
import com.nabil.ahmed.shari7a.ui.viewmodel.MainViewModel

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun DashboardScreen(viewModel: MainViewModel) {
    val billResult by viewModel.billResult.collectAsState()
    val kwh = billResult?.kwh ?: 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TopLogo()

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
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "مراقبة الشبكة في الوقت الفعلي // العقدة_041",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 24.dp)
        )

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
                    color = Color(0xFF00BFA5),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = kwh.toInt().toString(),
                        fontSize = 72.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "كيلوواط ساعة",
                        fontSize = 18.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 16.dp, start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                
                Surface(
                    color = Color(0xFFFF80AB).copy(alpha = 0.1f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "تم كشف حمل مرتفع",
                        color = Color(0xFFD81B60),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Multi-segment progress bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEEEEEE))
                ) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Segment(0.1f, Color(0xFF00BFA5))
                        Segment(0.2f, Color(0xFF4DB6AC))
                        Segment(0.3f, Color(0xFFFBC02D))
                        Segment(0.2f, Color(0xFFF4511E))
                        Segment(0.2f, Color(0xFFD81B60))
                    }
                    
                    // Indicator thumb
                    val indicatorPos = (kwh / 2000.0).toFloat().coerceIn(0f, 1f)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(indicatorPos)
                            .fillMaxHeight()
                    ) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .padding(3.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxSize().clip(CircleShape).background(Color(0xFFD81B60)))
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    IndicatorLabel("100", Color(0xFF00BFA5))
                    IndicatorLabel("650", Color(0xFFFBC02D))
                    IndicatorLabel("1000", Color(0xFFF4511E))
                    IndicatorLabel("2000", Color(0xFFD81B60))
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "ملف الطاقة",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
            color = Color(0xFFD81B60)
        )
        Text(
            text = "استهلاك الشبكة وتفاصيل التعرفة في الوقت الفعلي",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF00BFA5),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        EnergyZoneCard("منطقة الدعم", "0-100", "يتم تطبيق دعم \"الاستهلاك المنخفض\". علامة الخروج عند 100 كيلوواط ساعة.", Color(0xFF00BFA5), true)
        EnergyZoneCard("عادي", "101-650", "تحذير برتقالي رئيسي عند 650 كيلوواط ساعة. فترة التعرفة وشيكة.", Color(0xFF4DB6AC), false)
        EnergyZoneCard("تعريفة عالية", "651-1000", "تحذير أحمر عند 1000 كيلوواط ساعة. الانتقال إلى معدل 223 نقطة.", Color(0xFFFBC02D), false)
        EnergyZoneCard("حرج", "1001-2000", "أقصى خطر عند 2000 كيلوواط ساعة. الانتقال إلى معدل 258 نقطة.", Color(0xFFF4511E), false)
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun IndicatorLabel(text: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(2.dp, 8.dp).background(Color.LightGray))
        Text(text = text, style = MaterialTheme.typography.labelSmall, color = color, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun RowScope.Segment(weight: Float, color: Color) {
    Box(modifier = Modifier.weight(weight).fillMaxHeight().background(color))
}

@Composable
fun EnergyZoneCard(title: String, range: String, desc: String, color: Color, isSecure: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                if (isSecure) Icon(Icons.Rounded.Shield, contentDescription = null, tint = Color.LightGray)
                else Spacer(modifier = Modifier.size(24.dp))
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "المرحلة", style = MaterialTheme.typography.labelSmall, color = color)
                    Text(text = title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                }
            }
            
            Text(
                text = range,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
            Text(
                text = "ك.و.س/شهر",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth().height(4.dp).clip(CircleShape).background(color))
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
