package com.payslipmax.pdfparser.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.payslipmax.pdfparser.ui.theme.AppStrings

@Composable
fun StagingCard(
    onSeedClick: () -> Unit,
    onClearClick: () -> Unit,
    onCrashTestClick: (() -> Unit)? = null,
    onBackgroundCrashTestClick: (() -> Unit)? = null,
    onSimulateParserFailureClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        SettingsRow(
            icon = "🌱",
            title = AppStrings.settingsStagingSeedBtn,
            subtitle = AppStrings.settingsStagingDesc,
            onClick = onSeedClick,
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
        SettingsRow(
            icon = "🗑️",
            title = AppStrings.settingsStagingClearBtn,
            onClick = onClearClick,
        )
        if (onCrashTestClick != null) {
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            SettingsRow(
                icon = "💥",
                title = AppStrings.settingsStagingCrashTestBtn,
                onClick = onCrashTestClick,
            )
        }
        if (onBackgroundCrashTestClick != null) {
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            SettingsRow(
                icon = "⚡",
                title = com.payslipmax.pdfparser.ui.theme.AppStringsSupport.settingsStagingBackgroundCrashBtn,
                onClick = onBackgroundCrashTestClick,
            )
        }
        if (onSimulateParserFailureClick != null) {
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            SettingsRow(
                icon = "📋",
                title = com.payslipmax.pdfparser.ui.theme.AppStringsSupport.settingsStagingSimulateParserFailureBtn,
                onClick = onSimulateParserFailureClick,
            )
        }
    }
}
