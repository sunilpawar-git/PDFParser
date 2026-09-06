package com.payslipmax.pdfparser.telemetry

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DiagnosticReportGeneratorTest {
    @Test
    fun generatesExpectedDiagnosticFormat() {
        val report =
            DiagnosticReportGenerator.generateReport(
                appVersion = "1.0.0",
                installationId = "PMX-A7F39C",
                osVersion = "Android 15 (API 35)",
                deviceModel = "Pixel 9",
                cpuArch = "arm64-v8a",
                lastScreen = "Settings",
                lastOperation = "Idle",
            )

        assertTrue(report.contains("PayslipMax Diagnostic Report"))
        assertTrue(report.contains("App: 1.0.0"))
        assertTrue(report.contains("Installation ID: PMX-A7F39C"))
        assertTrue(report.contains("OS: Android 15 (API 35)"))
        assertTrue(report.contains("Device: Pixel 9"))
        assertTrue(report.contains("Architecture: arm64-v8a"))
        assertTrue(report.contains("Last screen: Settings"))
        assertTrue(report.contains("Last operation: Idle"))
    }

    @Test
    fun redactsAnyPiiOrFinancialInformationIfPresent() {
        val report =
            DiagnosticReportGenerator.generateReport(
                appVersion = "1.0.0",
                installationId = "PMX-A7F39C",
                osVersion = "Android 15",
                deviceModel = "Pixel 9",
                cpuArch = "arm64",
                lastScreen = "Settings",
                lastOperation = "Processed salary Rs. 95000 with ABCDE1234F",
            )

        assertFalse(report.contains("95000"))
        assertFalse(report.contains("ABCDE1234F"))
        assertTrue(report.contains("[REDACTED_AMOUNT]"))
        assertTrue(report.contains("[REDACTED_PAN]"))
    }
}
