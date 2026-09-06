package com.payslipmax.pdfparser.telemetry

import com.google.firebase.crashlytics.FirebaseCrashlytics

/**
 * Android implementation of [CrashReporter] wrapping Firebase Crashlytics.
 * Ensures all logs and metadata are sanitized before dispatch.
 */
class AndroidCrashReporter : CrashReporter {
    private val crashlytics: FirebaseCrashlytics?
        get() =
            try {
                FirebaseCrashlytics.getInstance()
            } catch (_: Exception) {
                null
            }

    override fun log(message: String) {
        val safeMessage = TelemetrySanitizer.sanitizeMessage(message)
        try {
            crashlytics?.log(safeMessage)
        } catch (_: Exception) {
        }
    }

    override fun setCustomKey(
        key: String,
        value: String,
    ) {
        if (!TelemetrySanitizer.isKeyAllowed(key)) return
        val safeValue = TelemetrySanitizer.sanitizeMessage(value)
        try {
            crashlytics?.setCustomKey(key.lowercase().trim(), safeValue)
        } catch (_: Exception) {
        }
    }

    override fun recordException(
        throwable: Throwable,
        metadata: Map<String, String>?,
    ) {
        try {
            val safeMetadata = TelemetrySanitizer.sanitizeMetadata(metadata)
            safeMetadata.forEach { (k, v) ->
                crashlytics?.setCustomKey(k, v)
            }
            crashlytics?.recordException(throwable)
        } catch (_: Exception) {
        }
    }

    override fun setUserId(userId: String) {
        try {
            crashlytics?.setUserId(userId)
        } catch (_: Exception) {
        }
    }
}

actual fun provideCrashReporter(): CrashReporter = AndroidCrashReporter()
