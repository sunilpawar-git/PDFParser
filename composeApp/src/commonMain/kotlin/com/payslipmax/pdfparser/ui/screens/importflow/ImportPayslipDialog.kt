package com.payslipmax.pdfparser.ui.screens.importflow

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.payslipmax.pdfparser.ui.ImportUiState
import com.payslipmax.pdfparser.ui.theme.AppDimensions
import com.payslipmax.pdfparser.ui.theme.AppStrings
import com.payslipmax.pdfparser.ui.theme.ImportStrings

@Composable
fun ImportPayslipDialog(
    importUiState: ImportUiState,
    onSelectFileClick: () -> Unit,
    onPasswordChanged: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onSubmitPassword: () -> Unit,
    onChooseDifferentFile: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier.fillMaxWidth().testTag("import_payslip_dialog"),
            shape = RoundedCornerShape(AppDimensions.CornerRadius),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(AppDimensions.BorderThin, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
        ) {
            Column(
                modifier = Modifier.padding(AppDimensions.PaddingMedium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingMedium),
            ) {
                ImportDialogHeader(
                    isPasswordStep = importUiState is ImportUiState.PasswordRequired,
                    onDismiss = onDismiss,
                )
                ImportDialogContent(
                    importUiState = importUiState,
                    onSelectFileClick = onSelectFileClick,
                    onPasswordChanged = onPasswordChanged,
                    onTogglePasswordVisibility = onTogglePasswordVisibility,
                    onSubmitPassword = onSubmitPassword,
                    onChooseDifferentFile = onChooseDifferentFile,
                    onDismiss = onDismiss,
                )
            }
        }
    }
}

@Composable
private fun ImportDialogHeader(
    isPasswordStep: Boolean,
    onDismiss: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier.padding(end = AppDimensions.IconSizeExtraLarge, start = AppDimensions.PaddingSmall),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = if (isPasswordStep) ImportStrings.unlockHeader else ImportStrings.importHeader,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.testTag("txt_dialog_title"),
            )
            Spacer(modifier = Modifier.height(AppDimensions.SpacingTiny))
            Text(
                text = ImportStrings.importSubtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
        IconButton(
            onClick = onDismiss,
            modifier = Modifier.align(Alignment.TopEnd).testTag("btn_close_dialog"),
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = ImportStrings.cdCloseDialog,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun ImportDialogContent(
    importUiState: ImportUiState,
    onSelectFileClick: () -> Unit,
    onPasswordChanged: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onSubmitPassword: () -> Unit,
    onChooseDifferentFile: () -> Unit,
    onDismiss: () -> Unit,
) {
    when (importUiState) {
        is ImportUiState.Idle -> {
            SelectFileStep(onSelectFileClick = onSelectFileClick)
        }
        is ImportUiState.InspectingFile -> {
            ImportProgressSection(message = ImportStrings.progressInspecting)
        }
        is ImportUiState.PasswordRequired -> {
            UnlockPdfStep(
                state = importUiState,
                onPasswordChanged = onPasswordChanged,
                onTogglePasswordVisibility = onTogglePasswordVisibility,
                onSubmitPassword = onSubmitPassword,
                onChooseDifferentFile = onChooseDifferentFile,
            )
        }
        is ImportUiState.Success -> {
            Text(
                text = AppStrings.uploadSuccess,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.testTag("txt_upload_success"),
            )
        }
        is ImportUiState.Failure -> {
            ImportFailureSection(
                message = importUiState.message,
                onRetry = onSelectFileClick,
                onDismiss = onDismiss,
            )
        }
    }
}

@Composable
private fun ImportProgressSection(message: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingSmall),
        modifier = Modifier.padding(vertical = AppDimensions.PaddingMedium),
    ) {
        CircularProgressIndicator(modifier = Modifier.size(AppDimensions.IconSizeMedium))
        Text(message, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun ImportFailureSection(
    message: String,
    onRetry: () -> Unit,
    onDismiss: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingSmall),
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.testTag("txt_failure_message"),
        )
        Row(horizontalArrangement = Arrangement.spacedBy(AppDimensions.SpacingSmall)) {
            Button(onClick = onRetry) {
                Text(ImportStrings.btnSelectPdf)
            }
            TextButton(onClick = onDismiss) {
                Text(AppStrings.uploadDismiss)
            }
        }
    }
}
