package com.payslipmax.pdfparser.telemetry

/**
 * Deliberately triggers a test crash to verify production Crashlytics pipeline connectivity.
 * Throws a [RuntimeException] with a distinctive tag.
 */
expect fun triggerTestCrash(): Nothing

/**
 * Deliberately triggers a test crash on an asynchronous background coroutine worker
 * to verify non-UI thread crash capture and Crashlytics thread attribution.
 */
expect fun triggerBackgroundTestCrash()
