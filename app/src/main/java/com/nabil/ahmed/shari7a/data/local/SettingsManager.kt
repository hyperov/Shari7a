package com.nabil.ahmed.shari7a.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        val PREVIOUS_READING = doublePreferencesKey("previous_reading")
        val CURRENT_READING = doublePreferencesKey("current_reading")
    }

    val previousReading: Flow<Double> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PREVIOUS_READING] ?: 0.0
        }

    val currentReading: Flow<Double> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[CURRENT_READING] ?: 0.0
        }

    suspend fun savePreviousReading(reading: Double) {
        dataStore.edit { preferences ->
            preferences[PREVIOUS_READING] = reading
        }
    }

    suspend fun saveCurrentReading(reading: Double) {
        dataStore.edit { preferences ->
            preferences[CURRENT_READING] = reading
        }
    }
}
