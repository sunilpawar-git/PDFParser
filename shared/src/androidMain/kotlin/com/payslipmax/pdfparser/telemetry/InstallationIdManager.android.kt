package com.payslipmax.pdfparser.telemetry

import android.content.Context
import com.payslipmax.pdfparser.crypto.ContextHolder

class AndroidInstallationIdStorage : InstallationIdStorage {
    private val prefsName = "payslipmax_telemetry_prefs"
    private val keyId = "anon_installation_id"

    override fun getInstallationId(): String? {
        val ctx = ContextHolder.context ?: return null
        val prefs = ctx.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        return prefs.getString(keyId, null)
    }

    override fun saveInstallationId(id: String) {
        val ctx = ContextHolder.context ?: return
        val prefs = ctx.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        prefs.edit().putString(keyId, id).apply()
    }
}

actual fun provideInstallationIdStorage(): InstallationIdStorage = AndroidInstallationIdStorage()
