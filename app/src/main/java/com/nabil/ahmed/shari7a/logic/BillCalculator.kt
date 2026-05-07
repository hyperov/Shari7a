package com.nabil.ahmed.shari7a.logic

import com.nabil.ahmed.shari7a.data.model.TariffData
import com.nabil.ahmed.shari7a.data.model.TariffTier

data class BillResult(
    val kwh: Double,
    val consumptionCost: Double,
    val serviceFee: Double,
    val totalCost: Double,
    val tier: TariffTier
)

object BillCalculator {
    private val tiers = TariffData.tiers

    fun calculate(kwh: Double): BillResult {
        if (kwh < 0) return BillResult(0.0, 0.0, 0.0, 0.0, tiers[0])

        val consumptionCost: Double
        val tier: TariffTier

        when {
            kwh <= 50 -> {
                tier = tiers[0]
                consumptionCost = kwh * (tier.pricePerKwhPiaster / 100.0)
            }
            kwh <= 100 -> {
                tier = tiers[1]
                consumptionCost = 50 * (tiers[0].pricePerKwhPiaster / 100.0) +
                        (kwh - 50) * (tiers[1].pricePerKwhPiaster / 100.0)
            }
            kwh <= 200 -> {
                tier = tiers[2]
                consumptionCost = kwh * (tier.pricePerKwhPiaster / 100.0)
            }
            kwh <= 350 -> {
                tier = tiers[3]
                consumptionCost = 200 * (tiers[2].pricePerKwhPiaster / 100.0) +
                        (kwh - 200) * (tiers[3].pricePerKwhPiaster / 100.0)
            }
            kwh <= 650 -> {
                tier = tiers[4]
                consumptionCost = 200 * (tiers[2].pricePerKwhPiaster / 100.0) +
                        150 * (tiers[3].pricePerKwhPiaster / 100.0) +
                        (kwh - 350) * (tiers[4].pricePerKwhPiaster / 100.0)
            }
            kwh <= 1000 -> {
                tier = tiers[5]
                consumptionCost = kwh * (tier.pricePerKwhPiaster / 100.0)
            }
            kwh <= 2000 -> {
                tier = tiers[6]
                consumptionCost = kwh * (tier.pricePerKwhPiaster / 100.0)
            }
            else -> {
                tier = tiers[7]
                consumptionCost = kwh * (tier.pricePerKwhPiaster / 100.0)
            }
        }

        return BillResult(
            kwh = kwh,
            consumptionCost = consumptionCost,
            serviceFee = tier.serviceFeeEgp,
            totalCost = consumptionCost + tier.serviceFeeEgp,
            tier = tier
        )
    }
}
