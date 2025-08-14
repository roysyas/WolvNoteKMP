package com.roys.wolvnotekmp.common

import platform.Foundation.NSUserDefaults

class IosPreferencesDataStore: PreferenceDataStore {
    private val defaults = NSUserDefaults.standardUserDefaults()

    override suspend fun saveString(key: String, value: String) {
        defaults.setObject(value, forKey = key)
    }

    override suspend fun getString(key: String): String? {
        return defaults.stringForKey(key)
    }
}