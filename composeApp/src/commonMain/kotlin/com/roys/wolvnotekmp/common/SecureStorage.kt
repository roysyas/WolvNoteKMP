package com.roys.wolvnotekmp.common

import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.AES
import kotlin.io.encoding.Base64

class SecureStorage(private val preferenceDataStore: PreferenceDataStore) {
    private val provider = CryptographyProvider.Companion.Default
    private val aes = provider.get(AES.GCM)

    suspend fun checkKey(): AES.GCM.Key{
        val key: AES.GCM.Key = if(preferenceDataStore.getString(Constants.PREFERENCES_PROVIDER_KEY).isNullOrEmpty()){
            generateKey(preferenceDataStore)
        }else{
            applyKey(preferenceDataStore.getString(Constants.PREFERENCES_PROVIDER_KEY)!!)
        }
        return key
    }

    private suspend fun generateKey(preferenceDataStore: PreferenceDataStore): AES.GCM.Key {
        val keyGenerator = aes.keyGenerator(keySize =  AES.Key.Size.B256)
        val key: AES.GCM.Key = keyGenerator.generateKey()
        val encodedKey: ByteArray = key.encodeToByteArray(AES.Key.Format.RAW)
        preferenceDataStore.saveString(Constants.PREFERENCES_PROVIDER_KEY, Base64.encode(encodedKey))
        return key
    }

    private suspend fun applyKey(keyString: String): AES.GCM.Key{
        val encodedKey = Base64.decode(keyString)
        return aes.keyDecoder().decodeFromByteArray(AES.Key.Format.RAW, encodedKey)
    }


    suspend fun encrypt(plainText: String, key: AES.GCM.Key): String {
        val ciphertext: ByteArray = key.cipher().encrypt(plaintext = plainText.encodeToByteArray())
        return Base64.encode(ciphertext)
    }

    suspend fun decrypt(cipherText: String, key: AES.GCM.Key): String {
        val encryptedBytes = Base64.decode(cipherText)
        val decryptedBytes = key.cipher().decrypt(encryptedBytes)
        return decryptedBytes.decodeToString()
    }
}