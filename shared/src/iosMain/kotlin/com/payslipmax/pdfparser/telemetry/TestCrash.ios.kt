package com.payslipmax.pdfparser.telemetry

actual fun triggerTestCrash(): Nothing {
    throw RuntimeException("PayslipMax Test Crash: Observability Verification")
}
