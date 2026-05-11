package com.nabil.ahmed.shari7a.ui.screens.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nabil.ahmed.shari7a.data.local.SettingsManager
import com.nabil.ahmed.shari7a.data.model.MeterType
import com.nabil.ahmed.shari7a.di.settingsDataStore
import com.nabil.ahmed.shari7a.ui.components.TopLogo
import com.nabil.ahmed.shari7a.ui.screens.input.components.ConsumptionIndicator
import com.nabil.ahmed.shari7a.ui.screens.input.components.InputCard
import com.nabil.ahmed.shari7a.ui.screens.input.components.ResultConsumptionCost
import com.nabil.ahmed.shari7a.ui.theme.Shari7aTheme
import com.nabil.ahmed.shari7a.ui.viewmodel.MainViewModel

@Composable
fun InputScreen(viewModel: MainViewModel) {
    val inputReading by viewModel.inputReading.collectAsState()
    val previousReading by viewModel.previousReading.collectAsState()
    val billResult by viewModel.billResult.collectAsState()
    val meterType by viewModel.meterType.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFBAFFE5))
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.End
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

        Text(
            text = "أدخل قراءة العداد الحالية لحساب التكلفة الفورية وحالة الشريحة.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF660029),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        // Input Card (Dark Green)
        InputCard(inputReading, viewModel, previousReading)

        Spacer(modifier = Modifier.height(24.dp))

        // Status Card (White)
        ConsumptionIndicator(billResult)

        Spacer(modifier = Modifier.height(24.dp))

        // Result Card (Cyan/Neon)
        ResultConsumptionCost(billResult)
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun InputScreenPreview() {
    Shari7aTheme {
        val context = LocalContext.current
        val settingsManager = SettingsManager(context.settingsDataStore)
        val viewModel = MainViewModel(settingsManager)
        InputScreen(viewModel)
    }
}
