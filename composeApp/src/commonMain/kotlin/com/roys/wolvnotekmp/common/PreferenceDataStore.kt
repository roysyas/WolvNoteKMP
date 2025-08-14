package com.roys.wolvnotekmp.common

interface PreferenceDataStore {
    suspend fun saveString(key: String, value: String)
    suspend fun getString(key: String): String?
}