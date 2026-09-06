package com.payslipmax.pdfparser.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.payslipmax.pdfparser.domain.SalaryCountdownUiModel
import com.payslipmax.pdfparser.ui.theme.AppDimensions
import com.payslipmax.pdfparser.ui.theme.SalaryRibbonStrings

@Composable
fun SalaryCountdownRibbon(
    countdown: SalaryCountdownUiModel,
    modifier: Modifier = Modifier,
) {
    val isDark = isSystemInDarkTheme()
    val surfaceColor = MaterialTheme.colorScheme.surface
    val primaryColor = MaterialTheme.colorScheme.primary
    val gradientColors = getRibbonGradientColors(primaryColor, surfaceColor, isDark)
    val borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)

    Card(
        modifier = modifier.fillMaxWidth().testTag("salary_countdown_ribbon"),
        shape = RoundedCornerShape(AppDimensions.CornerRadius),
        border = BorderStroke(AppDimensions.BorderThin, borderColor),
        colors = CardDefaults.cardColors(containerColor = surfaceColor),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(Brush.horizontalGradient(gradientColors))
                    .padding(AppDimensions.PaddingMedium),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingSmall)) {
                RibbonHeaderRow(countdown)
                RibbonProgressRow(countdown)
            }
        }
    }
}

@Composable
private fun RibbonHeaderRow(countdown: SalaryCountdownUiModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(AppDimensions.SpacingSmall),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = if (countdown.isPaydayToday) getCelebrationIcon() else getCalendarIcon(),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(AppDimensions.IconSizeMedium),
            )
            Column {
                Text(
                    text = SalaryRibbonStrings.salaryRibbonTitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "${SalaryRibbonStrings.salaryRibbonPaydayPrefix} ${countdown.paydayDateFormatted}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        RibbonBadge(countdown)
    }
}

@Composable
private fun RibbonBadge(countdown: SalaryCountdownUiModel) {
    val badgeContainerColor =
        if (countdown.isPaydayToday) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.secondary.copy(alpha = 0.18f)
        }
    val badgeTextColor =
        if (countdown.isPaydayToday) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.secondary
        }

    val badgeText =
        when {
            countdown.isPaydayToday -> SalaryRibbonStrings.salaryRibbonPaydayToday
            countdown.daysRemaining == 1 -> "1 ${SalaryRibbonStrings.salaryRibbonDayLeftSingleSuffix}"
            else -> "${countdown.daysRemaining} ${SalaryRibbonStrings.salaryRibbonDaysLeftSuffix}"
        }

    Surface(
        shape = CircleShape,
        color = badgeContainerColor,
        modifier = Modifier.testTag("salary_countdown_badge"),
    ) {
        Text(
            text = badgeText,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = badgeTextColor,
            modifier = Modifier.padding(horizontal = AppDimensions.SpacingMedium, vertical = AppDimensions.SpacingTiny),
        )
    }
}

@Composable
private fun RibbonProgressRow(countdown: SalaryCountdownUiModel) {
    LinearProgressIndicator(
        progress = { countdown.progressRatio },
        modifier =
            Modifier
                .fillMaxWidth()
                .height(AppDimensions.ProgressTrackHeight)
                .clip(RoundedCornerShape(AppDimensions.ProgressCornerRadius))
                .testTag("salary_countdown_progress"),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
    )
}

private fun getRibbonGradientColors(
    primaryAccent: Color,
    surface: Color,
    isDark: Boolean,
): List<Color> {
    return if (isDark) {
        listOf(
            surface,
            primaryAccent.copy(alpha = 0.08f),
            surface,
        )
    } else {
        listOf(
            surface,
            primaryAccent.copy(alpha = 0.04f),
            surface,
        )
    }
}

private fun getCalendarIcon(): ImageVector =
    ImageVector.Builder(
        name = "CalendarMonth",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(19f, 4f)
            horizontalLineToRelative(-1f)
            verticalLineTo(2f)
            horizontalLineToRelative(-2f)
            verticalLineToRelative(2f)
            horizontalLineTo(8f)
            verticalLineTo(2f)
            horizontalLineTo(6f)
            verticalLineToRelative(2f)
            horizontalLineTo(5f)
            curveTo(3.89f, 4f, 3.01f, 4.9f, 3.01f, 6f)
            lineTo(3f, 20f)
            curveToRelative(0f, 1.1f, 0.89f, 2f, 2f, 2f)
            horizontalLineToRelative(14f)
            curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
            verticalLineTo(6f)
            curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
            close()
            moveTo(19f, 20f)
            horizontalLineTo(5f)
            verticalLineTo(9f)
            horizontalLineToRelative(14f)
            verticalLineToRelative(11f)
            close()
            moveTo(19f, 7f)
            horizontalLineTo(5f)
            verticalLineTo(6f)
            horizontalLineToRelative(14f)
            verticalLineToRelative(1f)
            close()
        }
    }.build()

private fun getCelebrationIcon(): ImageVector =
    ImageVector.Builder(
        name = "Celebration",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(2f, 22f)
            lineToRelative(14f, -5f)
            lineToRelative(-9f, -9f)
            close()
            moveTo(14.53f, 12.53f)
            lineToRelative(5.59f, -5.59f)
            curveToRelative(0.49f, -0.49f, 1.28f, -0.49f, 1.77f, 0f)
            lineToRelative(0.59f, 0.59f)
            curveToRelative(0.49f, 0.49f, 0.49f, 1.28f, 0f, 1.77f)
            lineToRelative(-5.59f, 5.59f)
            close()
        }
    }.build()
