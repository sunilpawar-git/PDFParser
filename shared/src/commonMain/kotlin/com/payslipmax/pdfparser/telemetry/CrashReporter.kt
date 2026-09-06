package com.payslipmax.pdfparser.telemetry

/**
 * Unified crash & non-fatal observability contract for PayslipMax.
 * Provides custom logging/breadcrumbs, diagnostic keys, and non-fatal recording
 * while guaranteeing zero leakage of user personal or financial data.
 */
interface CrashReporter {
    /**
     * Records a diagnostic breadcrumb or navigation log.
     * Sanitizes [message] to remove any numbers or patterns that resemble financial/personal PII.
     */
    fun log(message: String)

    /**
     * Sets a non-PII diagnostic metadata key (e.g. "parser_version", "screen", "format_detected").
     * Rejects disallowed or PII-bearing keys.
     */
    fun setCustomKey(
        key: String,
        value: String,
    )

    /**
     * Records a caught non-fatal exception with optional safe metadata.
     */
    fun recordException(
        throwable: Throwable,
        metadata: Map<String, String>? = null,
    )

    /**
     * Sets a privacy-safe anonymous installation identifier.
     */
    fun setUserId(userId: String)
}

expect fun provideCrashReporter(): CrashReporter
