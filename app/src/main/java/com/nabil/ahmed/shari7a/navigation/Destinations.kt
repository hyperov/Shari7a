package com.nabil.ahmed.shari7a.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Destination : NavKey {
    @Serializable
    data object Forecast : Destination
    
    @Serializable
    data object Input : Destination
    
    @Serializable
    data object Tariff : Destination
}
