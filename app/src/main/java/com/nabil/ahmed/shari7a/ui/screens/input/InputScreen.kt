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
import com.nabil.ahmed.shari7a.di.settingsDataStore
import com.nabil.ahmed.shari7a.ui.components.TopLogo
import com.nabil.ahmed.shari7a.ui.screens.input.components.ConsumptionIndicator
import com.nabil.ahmed.shari7a.ui.screens.input.components.DetailRow
import com.nabil.ahmed.shari7a.ui.screens.input.components.InputCard
import com.nabil.ahmed.shari7a.ui.screens.input.components.ResultConsumptionCost
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
