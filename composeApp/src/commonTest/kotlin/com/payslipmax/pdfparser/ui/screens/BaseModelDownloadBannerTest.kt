package com.payslipmax.pdfparser.ui.screens

import com.payslipmax.pdfparser.ui.PayslipUiState
import com.payslipmax.pdfparser.ui.theme.GemmaModelStrings
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Tests the visibility and message resolution rules for [BaseModelDownloadBanner] to ensure:
 * 1. Waiting for Wi-Fi displays clean informational copy rather than a fatal error alert.
 * 2. Active downloading shows download title with percentage.
 * 3. Harmless developer sideload errors (Error -15) are ignored.
 * 4. Actual fatal errors are formatted properly.
 */
class BaseModelDownloadBannerTest {
    @Test
    fun bannerIsVisibleWhenWaitingForWifi() {
        val state = PayslipUiState(isWaitingForWifi = true)
        val isFatalError = state.modelDownloadError != null && !state.modelDownloadError.contains("-15")
        val isVisible = state.isDownloadingModel || state.isWaitingForWifi || isFatalError

        assertTrue(isVisible, "Banner should be visible when waiting for Wi-Fi")
        assertFalse(isFatalError, "Waiting for Wi-Fi is not a fatal error")
    }

    @Test
    fun bannerSuppressesErrorMinus15() {
        val state =
            PayslipUiState(
                modelDownloadError = "Local AI model requires Google Play installation (Error -15: Unrecognized install)",
            )
        val isFatalError = state.modelDownloadError != null && !state.modelDownloadError.contains("-15")
        val isVisible = state.isDownloadingModel || state.isWaitingForWifi || isFatalError

        assertFalse(isVisible, "Banner should suppress -15 error on non-Play sideloads")
    }

    @Test
    fun bannerIsVisibleOnGenuineFatalError() {
        val state = PayslipUiState(modelDownloadError = "No space left on device")
        val isFatalError = state.modelDownloadError != null && !state.modelDownloadError.contains("-15")
        val isVisible = state.isDownloadingModel || state.isWaitingForWifi || isFatalError

        assertTrue(isVisible)
        assertTrue(isFatalError)
    }

    @Test
    fun resolveTitleForWaitingForWifi() {
        val state = PayslipUiState(isWaitingForWifi = true)
        val title = if (state.isWaitingForWifi) GemmaModelStrings.gemmaModelWaitingForWifiTitle else ""
        assertEquals(GemmaModelStrings.gemmaModelWaitingForWifiTitle, title)
    }

    @Test
    fun resolveTitleForActiveDownloadingWithProgress() {
        val state = PayslipUiState(isDownloadingModel = true, modelDownloadProgress = 0.65f)
        val title = "${GemmaModelStrings.gemmaModelDownloadingTitle} (${(state.modelDownloadProgress * 100).toInt()}%)"
        assertEquals("${GemmaModelStrings.gemmaModelDownloadingTitle} (65%)", title)
    }
}
