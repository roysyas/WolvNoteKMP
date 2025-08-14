package com.roys.wolvnotekmp.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.prefs.Preferences

class JvmPreferenceDataStore: PreferenceDataStore {
    private val prefs = Preferences.userRoot().node("WolvNoteKmpPreferences")


    override suspend fun saveString(key: String, value: String) {
        withContext(Dispatchers.IO) {
            prefs.put(key, value)
        }
    }

    override suspend fun getString(key: String): String? {
        return withContext(Dispatchers.IO) {
            prefs.get(key, null)
        }
    }
}