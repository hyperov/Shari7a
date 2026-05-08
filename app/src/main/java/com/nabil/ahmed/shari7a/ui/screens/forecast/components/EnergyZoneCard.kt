package com.nabil.ahmed.shari7a.ui.screens.forecast.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nabil.ahmed.shari7a.ui.theme.Shari7aTheme

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

@Preview(showBackground = true)
@Composable
fun EnergyZoneCardPreview() {
    Shari7aTheme {
        EnergyZoneCard(
            title = "منطقة الدعم",
            range = "0-100",
            desc = "يتم تطبيق دعم \"الاستهلاك المنخفض\". علامة الخروج عند 100 كيلوواط ساعة.",
            color = Color(0xFF00BFA5),
            isSecure = true
        )
    }
}
