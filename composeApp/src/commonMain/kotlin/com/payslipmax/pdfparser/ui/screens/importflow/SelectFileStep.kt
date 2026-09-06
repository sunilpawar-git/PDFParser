package com.payslipmax.pdfparser.ui.screens.importflow

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.payslipmax.pdfparser.ui.theme.AppDimensions
import com.payslipmax.pdfparser.ui.theme.ImportStrings

@Composable
fun SelectFileStep(
    onSelectFileClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingMedium),
    ) {
        SelectFileCard(onSelectFileClick = onSelectFileClick)
        OfflinePrivacyNotice()
    }
}

@Composable
private fun SelectFileCard(
    onSelectFileClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimensions.CornerRadiusMedium),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
            ),
        border =
            BorderStroke(
                AppDimensions.BorderThin,
                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            ),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(AppDimensions.PaddingLarge),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingSmall),
        ) {
            Text(
                text = "📄",
                fontSize = AppDimensions.FontSizeEmojiMedium,
            )
            Text(
                text = ImportStrings.importFilePrompt,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
            Text(
                text = ImportStrings.importFileHint,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(AppDimensions.SpacingSmall))
            SelectFileButton(onSelectFileClick = onSelectFileClick)
        }
    }
}

@Composable
private fun SelectFileButton(onSelectFileClick: () -> Unit) {
    Button(
        onClick = onSelectFileClick,
        modifier =
            Modifier
                .fillMaxWidth()
                .testTag("btn_select_pdf"),
        shape = RoundedCornerShape(AppDimensions.CornerRadiusMedium),
    ) {
        Text(
            text = ImportStrings.btnSelectPdf,
            fontSize = AppDimensions.TextSizeButton,
        )
    }
}

@Composable
private fun OfflinePrivacyNotice() {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = AppDimensions.PaddingSmall),
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
            text = ImportStrings.importPrivacyNotice,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Start,
        )
    }
}
