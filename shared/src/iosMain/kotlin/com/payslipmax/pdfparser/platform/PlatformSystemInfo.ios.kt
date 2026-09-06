package com.payslipmax.pdfparser.platform

import platform.UIKit.UIDevice

actual fun getPlatformSystemInfo(): PlatformSystemInfo {
    val device = UIDevice.currentDevice
    val model = device.model
    val os = "${device.systemName} ${device.systemVersion}"
    val arch = "arm64"
    return PlatformSystemInfo(
        deviceModel = model,
        osVersion = os,
        cpuArchitecture = arch,
    )
}
