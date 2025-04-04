package com.helpyapps.unforgetty.notification

import com.helpyapps.unforgetty.R

enum class NotificationType(val iconResourceID: Int) {
    Alarm(R.drawable.alarm_icon),
    Vibration(R.drawable.vibration_icon),
    Silent(R.drawable.silence_icon)
}