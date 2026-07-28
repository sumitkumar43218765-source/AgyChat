package com.agychat.app.presentation.settings

sealed class SettingsUiEvent {
    data class UpdateAgyPath(val path: String) : SettingsUiEvent()
    data class UpdateIdleSettleDelay(val delayMs: Long) : SettingsUiEvent()
    object ValidateInstallation : SettingsUiEvent()
    object SaveSettings : SettingsUiEvent()
}
