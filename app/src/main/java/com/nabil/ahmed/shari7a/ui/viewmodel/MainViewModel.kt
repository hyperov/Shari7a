package com.nabil.ahmed.shari7a.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabil.ahmed.shari7a.data.local.SettingsManager
import com.nabil.ahmed.shari7a.logic.BillCalculator
import com.nabil.ahmed.shari7a.logic.BillResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val settingsManager: SettingsManager) : ViewModel() {

    val previousReading: StateFlow<Double> = settingsManager.previousReading
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val currentReading: StateFlow<Double> = settingsManager.currentReading
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    private val _inputReading = MutableStateFlow("")
    val inputReading: StateFlow<String> = _inputReading.asStateFlow()

    val billResult: StateFlow<BillResult?> = combine(inputReading, previousReading) { input, prev ->
        val current = input.toDoubleOrNull() ?: 0.0
        if (current >= prev) {
            BillCalculator.calculate(current - prev)
        } else {
            null
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun onInputReadingChanged(value: String) {
        _inputReading.value = value
    }

    fun saveReadings() {
        viewModelScope.launch {
            val current = _inputReading.value.toDoubleOrNull() ?: 0.0
            settingsManager.saveCurrentReading(current)
            // Optionally update previous reading if it's a new month, but for now we just persist
        }
    }
    
    fun setPreviousReading(value: Double) {
        viewModelScope.launch {
            settingsManager.savePreviousReading(value)
        }
    }
}
