package com.payslipmax.pdfparser.logging

import com.payslipmax.pdfparser.subscription.isDebugBuild

object Logger {
    private val crashReporter: com.payslipmax.pdfparser.telemetry.CrashReporter by lazy {
        com.payslipmax.pdfparser.telemetry.provideCrashReporter()
    }

    fun d(
        tag: String,
        message: String,
    ) {
        if (isDebugBuild()) {
            println("[DEBUG] [$tag] $message")
        }
    }

    fun w(
        tag: String,
        message: String,
    ) {
        println("[WARN] [$tag] $message")
        crashReporter.log("WARN [$tag] $message")
    }

    fun e(
        tag: String,
        message: String,
        throwable: Throwable? = null,
    ) {
        val errorMsg = throwable?.let { " - ${it.message}" } ?: ""
        println("[ERROR] [$tag] $message$errorMsg")
        crashReporter.log("ERROR [$tag] $message")
        if (throwable != null) {
            crashReporter.recordException(throwable, mapOf("error_tag" to tag))
        }
    }
}
