package com.payslipmax.pdfparser.telemetry

actual fun triggerTestCrash(): Nothing {
    throw RuntimeException("PayslipMax Test Crash: Observability Verification")
}

actual fun triggerBackgroundTestCrash() {
    throw RuntimeException("PayslipMax Background Crash: Non-UI Worker Exception")
}
