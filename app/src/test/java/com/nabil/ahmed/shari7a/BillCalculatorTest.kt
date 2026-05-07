package com.nabil.ahmed.shari7a

import com.nabil.ahmed.shari7a.logic.BillCalculator
import org.junit.Assert.assertEquals
import org.junit.Test

class BillCalculatorTest {

    @Test
    fun testTier1() {
        val result = BillCalculator.calculate(40.0)
        assertEquals(1, result.tier.id)
        assertEquals(40 * 0.68, result.consumptionCost, 0.01)
        assertEquals(1.0, result.serviceFee, 0.01)
        assertEquals(40 * 0.68 + 1.0, result.totalCost, 0.01)
    }

    @Test
    fun testTier2() {
        val result = BillCalculator.calculate(80.0)
        assertEquals(2, result.tier.id)
        val expectedCost = 50 * 0.68 + 30 * 0.78
        assertEquals(expectedCost, result.consumptionCost, 0.01)
        assertEquals(2.0, result.serviceFee, 0.01)
        assertEquals(expectedCost + 2.0, result.totalCost, 0.01)
    }

    @Test
    fun testTier3() {
        val result = BillCalculator.calculate(150.0)
        assertEquals(3, result.tier.id)
        assertEquals(150 * 0.95, result.consumptionCost, 0.01)
        assertEquals(6.0, result.serviceFee, 0.01)
        assertEquals(150 * 0.95 + 6.0, result.totalCost, 0.01)
    }

    @Test
    fun testTier4() {
        val result = BillCalculator.calculate(300.0)
        assertEquals(4, result.tier.id)
        val expectedCost = 200 * 0.95 + 100 * 1.55
        assertEquals(expectedCost, result.consumptionCost, 0.01)
        assertEquals(11.0, result.serviceFee, 0.01)
    }

    @Test
    fun testTier5() {
        val result = BillCalculator.calculate(500.0)
        assertEquals(5, result.tier.id)
        val expectedCost = 200 * 0.95 + 150 * 1.55 + 150 * 1.95
        assertEquals(expectedCost, result.consumptionCost, 0.01)
        assertEquals(15.0, result.serviceFee, 0.01)
    }

    @Test
    fun testTier6() {
        val result = BillCalculator.calculate(800.0)
        assertEquals(6, result.tier.id)
        assertEquals(800 * 2.10, result.consumptionCost, 0.01)
        assertEquals(25.0, result.serviceFee, 0.01)
    }
}
