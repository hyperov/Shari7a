package com.nabil.ahmed.shari7a.ui.screens.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nabil.ahmed.shari7a.data.local.SettingsManager
import com.nabil.ahmed.shari7a.ui.components.TopLogo
import com.nabil.ahmed.shari7a.ui.screens.input.components.DetailRow
import com.nabil.ahmed.shari7a.ui.theme.Shari7aTheme
import com.nabil.ahmed.shari7a.ui.viewmodel.MainViewModel
import java.util.Locale

@Composable
fun InputScreen(viewModel: MainViewModel) {
    val inputReading by viewModel.inputReading.collectAsState()
    val previousReading by viewModel.previousReading.collectAsState()
    val billResult by viewModel.billResult.collectAsState()

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
                append("لوحة ")
            }
            withStyle(style = SpanStyle(color = Color(0xFFD81B60))) {
                append("الإدخال")
            }
        }

        Text(
            text = headerText,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "أدخل قراءة العداد الحالية لحساب التكلفة الفورية وحالة الشريحة.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        // Input Card (Dark Green)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF004D40))
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "قراءة العداد الحالية",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BasicTextField(
                        value = inputReading,
                        onValueChange = { viewModel.onInputReadingChanged(it) },
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Black,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "كيلوواط",
                                    fontSize = 18.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                innerTextField()
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "القراءة السابقة: ${String.format(Locale.US, "%,.0f", previousReading)} كيلوواط (الشهر الماضي)",
                    color = Color.White.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Status Card (White)
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
                        color = Color(0xFFFF80AB).copy(alpha = 0.2f),
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
                    (billResult!!.kwh / (billResult!!.tier.maxKwh ?: 2000)).toFloat().coerceIn(0f, 1f)
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

                Text(
                    text = "الشريحة التالية: الشريحة ${if (billResult != null) billResult!!.tier.id + 1 else "-"} تبدأ قريباً.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Result Card (Cyan/Neon)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF00E5FF))
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                DetailRow("الطاقة المستهلكة", "${billResult?.kwh?.toInt() ?: 0} kWh")
                DetailRow("رسوم الخدمة", "${String.format(Locale.US, "%.2f", billResult?.serviceFee ?: 0.0)} EGP")
                
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
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun InputScreenPreview() {
    Shari7aTheme {
        val context = LocalContext.current
        val settingsManager = SettingsManager(context)
        val viewModel = MainViewModel(settingsManager)
        InputScreen(viewModel)
    }
}
