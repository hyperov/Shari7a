package com.nabil.ahmed.shari7a.data.model

import androidx.compose.ui.graphics.Color

enum class MeterType {
    LEGAL, // Regular tiered (قانوني)
    CODE   // Flat rate (كودي)
}

data class TariffTier(
    val id: Int,
    val name: String,
    val range: String,
    val minKwh: Int,
    val maxKwh: Int?,
    val pricePerKwhPiaster: Double,
    val serviceFeeEgp: Double,
    val color: Color
)

object TariffData {
    const val CODE_METER_RATE_PIASTER = 274.0 // 2.74 EGP

    val codeTier = TariffTier(0, "كودي", "سعر موحد", 0, null, CODE_METER_RATE_PIASTER, 0.0, Color(0xFF6200EE))

    val tiers = listOf(
        TariffTier(1, "١", "٠ - ٥٠", 0, 50, 68.0, 1.0, Color(0xFF00BFA5)),
        TariffTier(2, "٢", "٥١ - ١٠٠", 51, 100, 78.0, 2.0, Color(0xFF00897B)),
        TariffTier(3, "٣", "٠ - ٢٠٠", 0, 200, 95.0, 6.0, Color(0xFFFBC02D)),
        TariffTier(4, "٤", "٢٠١ - ٣٥٠", 201, 350, 155.0, 11.0, Color(0xFFF4511E)),
        TariffTier(5, "٥", "٣٥١ - ٦٥٠", 351, 650, 195.0, 15.0, Color(0xFFD81B60)),
        TariffTier(6, "٦", "٠ - ١٠٠٠", 0, 1000, 210.0, 25.0, Color(0xFFB71C1C)),
        TariffTier(7, "٧", "٠ - ٢٠٠٠", 0, 2000, 223.0, 40.0, Color(0xFF880E4F)),
        TariffTier(8, "٨", "أكثر من ٢٠٠٠", 2001, null, 258.0, 40.0, Color(0xFF4A148C))
    )
}
