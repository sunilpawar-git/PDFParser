package com.payslipmax.pdfparser.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Evaluates app integrity via [appIntegrityChecker] and updates [_uiState].
 */
fun PayslipViewModel.verifyAppIntegrity() {
    viewModelScope.launch {
        val status = appIntegrityChecker.checkIntegrity()
        if (!status.isAllowedToRun) {
            com.payslipmax.pdfparser.logging.Logger.w(
                "AppIntegrity",
                "Integrity violation detected: $status",
            )
        } else {
            com.payslipmax.pdfparser.logging.Logger.d("AppIntegrity", "Integrity verified: $status")
        }
        _uiState.update { it.copy(appIntegrityStatus = status) }
    }
}
