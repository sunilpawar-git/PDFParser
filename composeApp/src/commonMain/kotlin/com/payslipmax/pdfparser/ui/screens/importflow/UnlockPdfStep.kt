package com.payslipmax.pdfparser.ui.screens.importflow

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.payslipmax.pdfparser.ui.ImportUiState
import com.payslipmax.pdfparser.ui.theme.AppDimensions
import com.payslipmax.pdfparser.ui.theme.AppStrings
import com.payslipmax.pdfparser.ui.theme.ImportStrings

@Composable
fun UnlockPdfStep(
    state: ImportUiState.PasswordRequired,
    onPasswordChanged: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onSubmitPassword: () -> Unit,
    onChooseDifferentFile: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingMedium),
    ) {
        SelectedFileChip(fileName = state.fileName, formattedSize = state.formattedFileSize)
        Text(
            text = ImportStrings.unlockSubtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        PasswordInputField(
            state = state,
            onPasswordChanged = onPasswordChanged,
            onTogglePasswordVisibility = onTogglePasswordVisibility,
            onSubmitPassword = onSubmitPassword,
        )
        PcdaPasswordHint()
        InlineErrorSection(errorMessage = state.errorMessage)
        PasswordActionButtons(
            state = state,
            onSubmitPassword = onSubmitPassword,
            onChooseDifferentFile = onChooseDifferentFile,
        )
    }
}

@Composable
private fun SelectedFileChip(
    fileName: String,
    formattedSize: String,
) {
    Surface(
        shape = RoundedCornerShape(AppDimensions.CornerRadiusSmall),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        border = BorderStroke(AppDimensions.BorderThin, MaterialTheme.colorScheme.outlineVariant),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = AppDimensions.PaddingMedium, vertical = AppDimensions.PaddingSmall),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppDimensions.SpacingSmall),
        ) {
            Text(text = "📄", fontSize = AppDimensions.TextSizeLarge)
            Text(
                text = fileName,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f).testTag("txt_file_name"),
            )
            Text(
                text = formattedSize,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.testTag("txt_file_size"),
            )
        }
    }
}

@Composable
private fun PasswordInputField(
    state: ImportUiState.PasswordRequired,
    onPasswordChanged: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onSubmitPassword: () -> Unit,
) {
    var fieldValue by remember(state.passwordInput) {
        mutableStateOf(TextFieldValue(text = state.passwordInput, selection = TextRange(state.passwordInput.length)))
    }
    OutlinedTextField(
        value = fieldValue,
        onValueChange = {
            fieldValue = it.copy(selection = TextRange(it.text.length))
            onPasswordChanged(it.text)
        },
        label = { Text(ImportStrings.labelPdfPassword) },
        isError = state.errorMessage != null,
        visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            TextButton(
                onClick = onTogglePasswordVisibility,
                modifier = Modifier.testTag("btn_password_toggle"),
            ) {
                Text(if (state.isPasswordVisible) AppStrings.hidePasswordToggle else AppStrings.showPasswordToggle)
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { onSubmitPassword() }),
        modifier = Modifier.fillMaxWidth().testTag("input_pdf_password"),
        singleLine = true,
        enabled = !state.isDecrypting,
    )
}

@Composable
private fun PcdaPasswordHint() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = AppDimensions.PaddingSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppDimensions.SpacingSmall),
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(AppDimensions.IconSizeSmall),
        )
        Text(
            text = ImportStrings.hintPcdaPassword,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.testTag("txt_pcda_hint"),
        )
    }
}

@Composable
private fun InlineErrorSection(errorMessage: String?) {
    if (errorMessage != null) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().testTag("txt_error_message"),
        )
    }
}

@Composable
private fun PasswordActionButtons(
    state: ImportUiState.PasswordRequired,
    onSubmitPassword: () -> Unit,
    onChooseDifferentFile: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingSmall),
    ) {
        Button(
            onClick = onSubmitPassword,
            enabled = state.passwordInput.isNotBlank() && !state.isDecrypting,
            shape = RoundedCornerShape(AppDimensions.CornerRadiusMedium),
            modifier = Modifier.fillMaxWidth().testTag("btn_decrypt_import"),
        ) {
            if (state.isDecrypting) {
                CircularProgressIndicator(
                    modifier = Modifier.size(AppDimensions.IconSizeSmall),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = AppDimensions.BorderMedium,
                )
                Spacer(modifier = Modifier.width(AppDimensions.SpacingSmall))
                Text(ImportStrings.progressDecrypting, fontSize = AppDimensions.TextSizeButton)
            } else {
                Text(ImportStrings.btnDecryptAndImport, fontSize = AppDimensions.TextSizeButton)
            }
        }
        TextButton(
            onClick = onChooseDifferentFile,
            enabled = !state.isDecrypting,
            modifier = Modifier.testTag("btn_choose_different_file"),
        ) {
            Text(
                text = ImportStrings.btnChooseDifferentFile,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
