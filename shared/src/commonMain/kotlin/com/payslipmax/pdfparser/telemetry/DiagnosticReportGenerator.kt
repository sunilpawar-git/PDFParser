package com.payslipmax.pdfparser.telemetry

/**
 * Generates privacy-safe diagnostic reports for in-app problem reports and support requests.
 * Conforms strictly to zero-PII telemetry policies (Section 10 & 21).
 */
object DiagnosticReportGenerator {
    fun generateReport(
        appVersion: String,
        installationId: String,
        osVersion: String,
        deviceModel: String,
        cpuArch: String,
        lastScreen: String? = null,
        lastOperation: String? = null,
    ): String {
        val safeScreen = lastScreen?.let { TelemetrySanitizer.sanitizeMessage(it) } ?: "None"
        val safeOperation = lastOperation?.let { TelemetrySanitizer.sanitizeMessage(it) } ?: "None"

        return buildString {
            appendLine("PayslipMax Diagnostic Report")
            appendLine("----------------------------------------")
            appendLine("App: $appVersion")
            appendLine("Installation ID: $installationId")
            appendLine("OS: $osVersion")
            appendLine("Device: $deviceModel")
            appendLine("Architecture: $cpuArch")
            appendLine("Last screen: $safeScreen")
            appendLine("Last operation: $safeOperation")
            appendLine("----------------------------------------")
            appendLine("[Describe what happened below]")
            appendLine()
        }
    }
}
