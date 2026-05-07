package com.nabil.ahmed.shari7a.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.TrendingDown
import androidx.compose.material.icons.rounded.AutoGraph
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nabil.ahmed.shari7a.ui.components.TopLogo

@Composable
fun ForecastScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TopLogo()

        Text(
            text = "التوقعات الذكية",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "بناءً على نمط استهلاكك الحالي، إليك ما نتوقعه لنهاية الشهر.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Saving Tip Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1))
        ) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.AutoMirrored.Rounded.TrendingDown, contentDescription = null, tint = Color(0xFF00897B), modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = "نصيحة توفير", fontWeight = FontWeight.Bold, color = Color(0xFF00695C))
                    Text(text = "قلل استهلاكك بنسبة ١٠٪ لتجنب الانتقال للشريحة الأعلى.", style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Forecast Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Rounded.AutoGraph, contentDescription = null, tint = Color(0xFFD81B60), modifier = Modifier.size(48.dp))
                Text(text = "الفاتورة المتوقعة", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
                Text(text = "٧٥٠.٠٠ ج.م", style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Black, color = Color(0xFFD81B60))
                
                Spacer(modifier = Modifier.height(16.dp))
                
                LinearProgressIndicator(
                    progress = { 0.65f },
                    modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                    color = Color(0xFFD81B60),
                    trackColor = Color(0xFFFCE4EC)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "تم استهلاك ٦٥٪ من ميزانية الشهر", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        
        // Placeholder for History
        Text(text = "سجل الاستهلاك", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        
        repeat(3) {
            HistoryItem("أبريل ٢٠٢٤", "٦٢٠ كيلوواط", "٥٤٠ ج.م")
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun HistoryItem(month: String, kwh: String, cost: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(text = month, fontWeight = FontWeight.Bold)
                Text(text = kwh, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Text(text = cost, fontWeight = FontWeight.Black, color = Color(0xFF00BFA5))
        }
    }
}
