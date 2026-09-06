package com.payslipmax.pdfparser.telemetry

import platform.Foundation.NSUserDefaults

class IosInstallationIdStorage : InstallationIdStorage {
    private val keyId = "anon_installation_id"

    override fun getInstallationId(): String? {
        return NSUserDefaults.standardUserDefaults.stringForKey(keyId)
    }

    override fun saveInstallationId(id: String) {
        NSUserDefaults.standardUserDefaults.setObject(id, forKey = keyId)
    }
}

actual fun provideInstallationIdStorage(): InstallationIdStorage = IosInstallationIdStorage()
