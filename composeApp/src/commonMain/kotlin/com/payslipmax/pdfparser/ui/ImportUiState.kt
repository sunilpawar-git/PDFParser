package com.payslipmax.pdfparser.ui

import com.payslipmax.pdfparser.domain.ParsedPayslip

/**
 * Represents the distinct UI states for the "File First, Password Second" payslip import workflow.
 * Upholds SSOT and MVVM principles.
 */
sealed interface ImportUiState {
    /** Initial dialog state: Prompting officer to select a PDF file */
    object Idle : ImportUiState

    /** Temporary state while inspecting the picked file's encryption */
    data class InspectingFile(
        val fileName: String,
    ) : ImportUiState

    /** PDF requires password to decrypt (Screen B) */
    data class PasswordRequired(
        val fileName: String,
        val formattedFileSize: String,
        val passwordInput: String = "",
        val isPasswordVisible: Boolean = false,
        val errorMessage: String? = null,
        val isDecrypting: Boolean = false,
    ) : ImportUiState

    /** Payslip was successfully decrypted and imported */
    data class Success(
        val payslip: ParsedPayslip,
    ) : ImportUiState

    /** Error state (e.g. invalid PDF header or unrecoverable error) */
    data class Failure(
        val message: String,
        val canRetry: Boolean = true,
    ) : ImportUiState
}
