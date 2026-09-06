package com.payslipmax.pdfparser.telemetry

/**
 * Deliberately triggers a test crash to verify production Crashlytics pipeline connectivity.
 * Throws a [RuntimeException] with a distinctive tag.
 */
expect fun triggerTestCrash(): Nothing
