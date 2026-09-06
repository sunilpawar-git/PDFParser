package com.payslipmax.pdfparser.ui.theme

/**
 * Dedicated string resources for the "File First, Password Second" payslip import workflow.
 * Kept in a separate file to enforce the <300 LOC limit across theme files.
 */
object ImportStrings {
    const val importHeader = "Import PCDA(O) Payslip"
    const val importSubtitle = "Secure, 100% offline-first parsing engine"
    const val importFilePrompt = "Select your Payslip PDF"
    const val importFileHint = "Stored on Device Files, Downloads, or Drive"
    const val btnSelectPdf = "Select PDF Payslip"
    const val importPrivacyNotice = "We parse your PDF locally on device. No documents ever leave your phone."

    const val unlockHeader = "Unlock Payslip"
    const val unlockSubtitle = "This payslip is password-protected. Please enter the password to decrypt it."
    const val labelPdfPassword = "Enter PDF Password"
    const val hintPcdaPassword = "Password is given by PCDA(O)"
    const val btnDecryptAndImport = "Decrypt & Import"
    const val btnChooseDifferentFile = "Choose a different file"
    const val errIncorrectPassword = "Incorrect password. Please try again."
    const val errInvalidPdf = "The selected file is not a valid PDF document."
    const val progressDecrypting = "Decrypting and verifying payslip..."
    const val progressInspecting = "Inspecting PDF document..."
    const val cdCloseDialog = "Close Dialog"
}
