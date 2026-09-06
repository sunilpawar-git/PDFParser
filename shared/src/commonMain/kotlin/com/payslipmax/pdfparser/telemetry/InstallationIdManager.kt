package com.payslipmax.pdfparser.telemetry

import kotlin.random.Random

/**
 * Persistence abstraction for the anonymous installation identifier.
 */
interface InstallationIdStorage {
    fun getInstallationId(): String?

    fun saveInstallationId(id: String)
}

/**
 * Expect function providing platform-specific persistent storage for [InstallationIdStorage].
 */
expect fun provideInstallationIdStorage(): InstallationIdStorage

/**
 * Manages the anonymous installation identifier for PayslipMax.
 * Guarantees zero PII linkage, formatted as `PMX-XXXXXX` (e.g. `PMX-A7F39C`).
 */
class InstallationIdManager(
    private val storage: InstallationIdStorage = provideInstallationIdStorage(),
) {
    private var cachedId: String? = null

    fun getOrCreateInstallationId(): String {
        val cached = cachedId
        if (cached != null) return cached

        val stored = storage.getInstallationId()
        if (!stored.isNullOrBlank() && stored.matches(ID_REGEX)) {
            cachedId = stored
            return stored
        }

        val newId = generateRandomId()
        storage.saveInstallationId(newId)
        cachedId = newId
        return newId
    }

    private fun generateRandomId(): String {
        val chars = "0123456789ABCDEFGHJKLMNPQRSTUVWXYZ"
        val randomSuffix =
            (1..6)
                .map { chars[Random.nextInt(chars.length)] }
                .joinToString("")
        return "PMX-$randomSuffix"
    }

    companion object {
        private val ID_REGEX = Regex("^PMX-[A-Z0-9]{6}$")
    }
}
