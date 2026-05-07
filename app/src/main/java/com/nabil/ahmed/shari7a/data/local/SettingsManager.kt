package com.nabil.ahmed.shari7a.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsManager(private val context: Context) {

    companion object {
        val PREVIOUS_READING = doublePreferencesKey("previous_reading")
        val CURRENT_READING = doublePreferencesKey("current_reading")
    }

    val previousReading: Flow<Double> = context.dataStore.data.map { preferences ->
        preferences[PREVIOUS_READING] ?: 0.0
    }

    val currentReading: Flow<Double> = context.dataStore.data.map { preferences ->
        preferences[CURRENT_READING] ?: 0.0
    }

    suspend fun savePreviousReading(reading: Double) {
        context.dataStore.edit { preferences ->
            preferences[PREVIOUS_READING] = reading
        }
    }

    suspend fun saveCurrentReading(reading: Double) {
        context.dataStore.edit { preferences ->
            preferences[CURRENT_READING] = reading
        }
    }
}
