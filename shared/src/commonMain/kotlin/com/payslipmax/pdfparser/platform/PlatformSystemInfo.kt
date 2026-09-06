package com.payslipmax.pdfparser.platform

/**
 * Platform system diagnostic metadata (hardware model, OS version, CPU architecture).
 */
data class PlatformSystemInfo(
    val deviceModel: String,
    val osVersion: String,
    val cpuArchitecture: String,
)

expect fun getPlatformSystemInfo(): PlatformSystemInfo
