package com.payslipmax.pdfparser.platform

import android.os.Build

actual fun getPlatformSystemInfo(): PlatformSystemInfo {
    val model = "${Build.MANUFACTURER} ${Build.MODEL}".trim()
    val os = "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"
    val arch = Build.SUPPORTED_ABIS.firstOrNull() ?: System.getProperty("os.arch") ?: "unknown"
    return PlatformSystemInfo(
        deviceModel = model,
        osVersion = os,
        cpuArchitecture = arch,
    )
}
