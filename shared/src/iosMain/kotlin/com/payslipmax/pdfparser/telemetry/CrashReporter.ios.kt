package com.payslipmax.pdfparser.telemetry

/**
 * iOS implementation of [CrashReporter].
 * Dispatches to a registrable delegate or provides a safe no-op.
 */
class IosCrashReporter : CrashReporter {
    override fun log(message: String) {
        val safeMessage = TelemetrySanitizer.sanitizeMessage(message)
        delegate?.log(safeMessage)
    }

    override fun setCustomKey(
        key: String,
        value: String,
    ) {
        if (!TelemetrySanitizer.isKeyAllowed(key)) return
        val safeValue = TelemetrySanitizer.sanitizeMessage(value)
        delegate?.setCustomKey(key.lowercase().trim(), safeValue)
    }

    override fun recordException(
        throwable: Throwable,
        metadata: Map<String, String>?,
    ) {
        val safeMetadata = TelemetrySanitizer.sanitizeMetadata(metadata)
        delegate?.recordException(throwable, safeMetadata)
    }

    override fun setUserId(userId: String) {
        delegate?.setUserId(userId)
    }

    companion object {
        var delegate: IosCrashReporterDelegate? = null
    }
}

interface IosCrashReporterDelegate {
    fun log(message: String)

    fun setCustomKey(
        key: String,
        value: String,
    )

    fun recordException(
        throwable: Throwable,
        metadata: Map<String, String>,
    )

    fun setUserId(userId: String)
}

actual fun provideCrashReporter(): CrashReporter = IosCrashReporter()
