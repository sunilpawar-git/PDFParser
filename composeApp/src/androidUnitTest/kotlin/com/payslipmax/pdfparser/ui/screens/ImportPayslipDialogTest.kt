package com.payslipmax.pdfparser.ui.screens

import androidx.compose.ui.test.*
import com.payslipmax.pdfparser.ui.ImportUiState
import com.payslipmax.pdfparser.ui.screens.importflow.ImportPayslipDialog
import com.payslipmax.pdfparser.ui.theme.ImportStrings
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ImportPayslipDialogTest {
    @AfterTest
    fun tearDown() {
        try {
            org.koin.core.context.stopKoin()
        } catch (_: Exception) {
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testSelectFileStep_rendersCorrectly() =
        runComposeUiTest {
            var fileClicked = false
            setContent {
                ImportPayslipDialog(
                    importUiState = ImportUiState.Idle,
                    onSelectFileClick = { fileClicked = true },
                    onPasswordChanged = {},
                    onTogglePasswordVisibility = {},
                    onSubmitPassword = {},
                    onChooseDifferentFile = {},
                    onDismiss = {},
                )
            }

            // Verify Step 1 content is visible
            onNodeWithText(ImportStrings.importHeader).assertExists()
            onNodeWithText(ImportStrings.importFilePrompt).assertExists()
            onNodeWithTag("btn_select_pdf").assertExists()

            // Verify password field is absent in step 1
            onNodeWithTag("input_pdf_password").assertDoesNotExist()

            // Verify click callback triggers
            onNodeWithTag("btn_select_pdf").performClick()
            assertTrue(fileClicked)
        }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testUnlockPdfStep_showsPcdaHintAndFileInfo() =
        runComposeUiTest {
            val state =
                ImportUiState.PasswordRequired(
                    fileName = "payslip_aug_2026.pdf",
                    formattedFileSize = "142 KB",
                    passwordInput = "",
                )
            setContent {
                ImportPayslipDialog(
                    importUiState = state,
                    onSelectFileClick = {},
                    onPasswordChanged = {},
                    onTogglePasswordVisibility = {},
                    onSubmitPassword = {},
                    onChooseDifferentFile = {},
                    onDismiss = {},
                )
            }

            // Verify header and file metadata
            onNodeWithText(ImportStrings.unlockHeader).assertExists()
            onNodeWithText("payslip_aug_2026.pdf").assertExists()
            onNodeWithText("142 KB").assertExists()

            // Verify the explicit PCDA hint
            onNodeWithText(ImportStrings.hintPcdaPassword).assertExists()

            // Verify "Decrypt & Import" button is disabled when password is empty
            onNodeWithTag("btn_decrypt_import").assertIsNotEnabled()
        }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testUnlockPdfStep_enablesButtonOnInputAndSubmits() =
        runComposeUiTest {
            var submitted = false
            val state =
                ImportUiState.PasswordRequired(
                    fileName = "payslip_aug_2026.pdf",
                    formattedFileSize = "142 KB",
                    passwordInput = "samplePassword",
                )
            setContent {
                ImportPayslipDialog(
                    importUiState = state,
                    onSelectFileClick = {},
                    onPasswordChanged = {},
                    onTogglePasswordVisibility = {},
                    onSubmitPassword = { submitted = true },
                    onChooseDifferentFile = {},
                    onDismiss = {},
                )
            }

            // Verify button is enabled with input
            onNodeWithTag("btn_decrypt_import").assertIsEnabled()
            onNodeWithTag("btn_decrypt_import").performClick()
            assertTrue(submitted)
        }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testUnlockPdfStep_showsInlineErrorMessage() =
        runComposeUiTest {
            val state =
                ImportUiState.PasswordRequired(
                    fileName = "payslip_aug_2026.pdf",
                    formattedFileSize = "142 KB",
                    passwordInput = "wrongPassword",
                    errorMessage = ImportStrings.errIncorrectPassword,
                )
            setContent {
                ImportPayslipDialog(
                    importUiState = state,
                    onSelectFileClick = {},
                    onPasswordChanged = {},
                    onTogglePasswordVisibility = {},
                    onSubmitPassword = {},
                    onChooseDifferentFile = {},
                    onDismiss = {},
                )
            }

            onNodeWithText(ImportStrings.errIncorrectPassword).assertExists()
        }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testChooseDifferentFile_triggersCallback() =
        runComposeUiTest {
            var chooseDifferentClicked = false
            val state =
                ImportUiState.PasswordRequired(
                    fileName = "payslip_aug_2026.pdf",
                    formattedFileSize = "142 KB",
                )
            setContent {
                ImportPayslipDialog(
                    importUiState = state,
                    onSelectFileClick = {},
                    onPasswordChanged = {},
                    onTogglePasswordVisibility = {},
                    onSubmitPassword = {},
                    onChooseDifferentFile = { chooseDifferentClicked = true },
                    onDismiss = {},
                )
            }

            onNodeWithTag("btn_choose_different_file").performClick()
            assertTrue(chooseDifferentClicked)
        }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testCloseButton_triggersDismiss() =
        runComposeUiTest {
            var dismissed = false
            setContent {
                ImportPayslipDialog(
                    importUiState = ImportUiState.Idle,
                    onSelectFileClick = {},
                    onPasswordChanged = {},
                    onTogglePasswordVisibility = {},
                    onSubmitPassword = {},
                    onChooseDifferentFile = {},
                    onDismiss = { dismissed = true },
                )
            }

            onNodeWithTag("btn_close_dialog").performClick()
            assertTrue(dismissed)
        }
}
