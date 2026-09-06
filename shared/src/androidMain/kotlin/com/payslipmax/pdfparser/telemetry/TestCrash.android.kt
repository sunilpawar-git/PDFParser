package com.payslipmax.pdfparser.telemetry

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

actual fun triggerTestCrash(): Nothing {
    throw RuntimeException("PayslipMax Test Crash: Observability Verification")
}

actual fun triggerBackgroundTestCrash() {
    CoroutineScope(Dispatchers.Default).launch {
        throw RuntimeException("PayslipMax Background Crash: Dispatchers.Default Worker Exception")
    }
}
