package com.payslipmax.pdfparser.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.payslipmax.pdfparser.domain.SalaryCountdownCalculator
import com.payslipmax.pdfparser.ui.theme.AppDimensions
import com.payslipmax.pdfparser.ui.theme.AppStrings

@Composable
fun EmptyDashboardPlaceholder(
    modifier: Modifier = Modifier,
    onReportIssueClick: (() -> Unit)? = null,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(AppDimensions.PaddingMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val countdown = remember { SalaryCountdownCalculator.getCurrentCountdown() }
        SalaryCountdownRibbon(countdown = countdown)

        Column(
            modifier = Modifier.weight(1f).padding(AppDimensions.PaddingLarge),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "📄",
                fontSize = AppDimensions.FontSizeEmoji,
                modifier = Modifier.padding(bottom = AppDimensions.SpacingLarge),
            )
            Text(
                text = AppStrings.dashboardEmptyStateTitle,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(AppDimensions.SpacingSmall))
            Text(
                text = AppStrings.dashboardEmptyStateDesc,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = AppDimensions.PaddingLarge),
            )
            Spacer(modifier = Modifier.height(AppDimensions.SpacingHuge))
            Text(
                text = AppStrings.dashboardEmptyStateLabel,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}
