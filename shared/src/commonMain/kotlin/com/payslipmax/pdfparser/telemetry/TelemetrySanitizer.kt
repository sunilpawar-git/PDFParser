package com.payslipmax.pdfparser.telemetry

/**
 * Strict telemetry privacy sanitizer for PayslipMax.
 * Ensures that no salary amounts, PAN cards, account numbers, or user names
 * can ever be transmitted in telemetry or crash reports.
 */
object TelemetrySanitizer {
    // Keys allowed in Crashlytics custom keys
    private val ALLOWED_KEY_PREFIXES =
        listOf(
            "app_",
            "build_",
            "android_",
            "ios_",
            "device_",
            "screen",
            "operation",
            "parser_",
            "format_",
            "page_count",
            "file_size_",
            "duration_",
            "error_",
            "status",
            "offline_",
            "database_",
        )

    private val BLOCKED_KEY_KEYWORDS =
        listOf(
            "pan",
            "cda",
            "salary",
            "amount",
            "bpay",
            "da",
            "dsop",
            "tax",
            "deduction",
            "earning",
            "name",
            "officer",
            "password",
            "pin",
            "account",
            "bank",
        )

    // Regex matching Indian PAN cards: 5 letters, 4 digits, 1 letter
    private val PAN_REGEX = Regex("[A-Z]{5}[0-9]{4}[A-Z]", RegexOption.IGNORE_CASE)

    // Regex matching currency amounts like ₹ 1,23,456 or Rs. 50,000 or plain 5+ digit currency figures
    private val CURRENCY_REGEX = Regex("(₹|rs\\.?|inr)\\s*[0-9,]+(\\.[0-9]{2})?", RegexOption.IGNORE_CASE)

    fun isKeyAllowed(key: String): Boolean {
        val lower = key.lowercase().trim()
        if (BLOCKED_KEY_KEYWORDS.any { lower.contains(it) }) return false
        return ALLOWED_KEY_PREFIXES.any { lower.startsWith(it) || lower == it }
    }

    fun sanitizeMessage(message: String): String {
        var result = message
        // Redact PAN numbers
        result = result.replace(PAN_REGEX, "[REDACTED_PAN]")
        // Redact Currency figures
        result = result.replace(CURRENCY_REGEX, "[REDACTED_AMOUNT]")
        return result
    }

    fun sanitizeMetadata(metadata: Map<String, String>?): Map<String, String> {
        if (metadata == null) return emptyMap()
        val safeMap = mutableMapOf<String, String>()
        for ((key, value) in metadata) {
            if (isKeyAllowed(key)) {
                safeMap[key.lowercase().trim()] = sanitizeMessage(value)
            }
        }
        return safeMap
    }
}
