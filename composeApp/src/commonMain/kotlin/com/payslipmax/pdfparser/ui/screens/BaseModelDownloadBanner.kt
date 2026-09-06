package com.payslipmax.pdfparser.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.payslipmax.pdfparser.ui.PayslipUiState
import com.payslipmax.pdfparser.ui.theme.AppDimensions
import com.payslipmax.pdfparser.ui.theme.GemmaModelStrings

/**
 * Modern, non-blocking Material 3 banner for Tier 6 Gemma model background installation.
 * Conforms to Android & Google Play Asset Delivery guidelines:
 * - When waiting for Wi-Fi on cellular, informs the user without alarming red errors and offers a
 *   direct [onResumeDownload] action button to confirm cellular data download via Play Core.
 * - When actively downloading, shows real-time progress.
 * - Hides harmless developer sideload errors (Error -15) so debug APKs remain clean.
 */
@Composable
fun BaseModelDownloadBanner(
    uiState: PayslipUiState,
    modifier: Modifier = Modifier,
    onResumeDownload: () -> Unit = {},
) {
    val isFatalError = uiState.modelDownloadError != null && !uiState.modelDownloadError.contains("-15")
    val isVisible = uiState.isDownloadingModel || uiState.isWaitingForWifi || isFatalError
    if (!isVisible) return

    val containerColor =
        when {
            isFatalError -> MaterialTheme.colorScheme.errorContainer
            uiState.isWaitingForWifi -> MaterialTheme.colorScheme.secondaryContainer
            else -> MaterialTheme.colorScheme.surfaceVariant
        }
    val contentColor =
        when {
            isFatalError -> MaterialTheme.colorScheme.onErrorContainer
            uiState.isWaitingForWifi -> MaterialTheme.colorScheme.onSecondaryContainer
            else -> MaterialTheme.colorScheme.onSurfaceVariant
        }

    Card(
        modifier = modifier.fillMaxWidth().padding(horizontal = AppDimensions.PaddingMedium, vertical = AppDimensions.SpacingTiny),
        shape = RoundedCornerShape(AppDimensions.CornerRadiusMedium),
        colors = CardDefaults.cardColors(containerColor = containerColor, contentColor = contentColor),
        border = BorderStroke(AppDimensions.BorderThin, contentColor.copy(alpha = 0.12f)),
    ) {
        Column(modifier = Modifier.padding(AppDimensions.PaddingMedium)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppDimensions.SpacingSmall),
            ) {
                Icon(
                    imageVector = if (isFatalError) Icons.Outlined.Info else Icons.Outlined.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(AppDimensions.IconSizeMedium),
                    tint = contentColor,
                )
                Text(
                    text = resolveBannerTitle(uiState, isFatalError),
                    style = MaterialTheme.typography.titleSmall,
                    color = contentColor,
                    modifier = Modifier.weight(1f),
                )
            }

            Spacer(modifier = Modifier.height(AppDimensions.SpacingTiny))

            Text(
                text = resolveBannerSubtitle(uiState, isFatalError),
                style = MaterialTheme.typography.bodySmall,
                color = contentColor.copy(alpha = 0.85f),
            )

            if (uiState.isDownloadingModel) {
                LinearProgressIndicator(
                    progress = { uiState.modelDownloadProgress },
                    modifier = Modifier.fillMaxWidth().padding(top = AppDimensions.SpacingSmall),
                )
            }

            if (uiState.isWaitingForWifi || isFatalError) {
                Spacer(modifier = Modifier.height(AppDimensions.SpacingSmall))
                FilledTonalButton(
                    onClick = onResumeDownload,
                    colors =
                        ButtonDefaults.filledTonalButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                    modifier = Modifier.align(Alignment.End),
                ) {
                    Text(
                        text =
                            if (uiState.isWaitingForWifi) {
                                GemmaModelStrings.gemmaModelDownloadCellularAction
                            } else {
                                GemmaModelStrings.gemmaModelRetryAction
                            },
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }

            Text(
                text = "${GemmaModelStrings.gemmaLicenseNoticeTitle}: ${GemmaModelStrings.gemmaTermsOfUseNotice}",
                style = MaterialTheme.typography.labelSmall,
                color = contentColor.copy(alpha = 0.6f),
                modifier = Modifier.padding(top = AppDimensions.SpacingTiny),
            )
        }
    }
}

private fun resolveBannerTitle(
    uiState: PayslipUiState,
    isFatalError: Boolean,
): String =
    when {
        isFatalError -> GemmaModelStrings.gemmaModelDownloadErrorTitle
        uiState.isWaitingForWifi -> GemmaModelStrings.gemmaModelWaitingForWifiTitle
        uiState.modelDownloadProgress > 0f -> "${GemmaModelStrings.gemmaModelDownloadingTitle} (${(uiState.modelDownloadProgress * 100).toInt()}%)"
        else -> GemmaModelStrings.gemmaModelDownloadingTitle
    }

private fun resolveBannerSubtitle(
    uiState: PayslipUiState,
    isFatalError: Boolean,
): String =
    when {
        isFatalError -> uiState.modelDownloadError.orEmpty()
        uiState.isWaitingForWifi -> GemmaModelStrings.gemmaModelWaitingForWifiSubtitle
        else -> GemmaModelStrings.gemmaModelDownloadBannerMessage
    }
