package com.roys.wolvnotekmp.common

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class AndroidPreferencesDataStore(private val context: Context): PreferenceDataStore {
    private val Context.dataStore by preferencesDataStore(name = Constants.PREFERENCES_NAME)

    override suspend fun saveString(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit { prefs ->
            prefs[dataStoreKey] = value
        }
    }

    override suspend fun getString(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val prefs = context.dataStore.data.first()
        return prefs[dataStoreKey]
    }

}