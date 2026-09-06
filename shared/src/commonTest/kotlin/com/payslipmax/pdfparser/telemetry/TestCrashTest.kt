package com.payslipmax.pdfparser.telemetry

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TestCrashTest {
    @Test
    fun triggerTestCrash_throwsExpectedRuntimeException() {
        val exception =
            assertFailsWith<RuntimeException> {
                triggerTestCrash()
            }
        assertEquals("PayslipMax Test Crash: Observability Verification", exception.message)
    }
}
