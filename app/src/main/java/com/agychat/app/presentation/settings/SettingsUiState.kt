package com.agychat.app.presentation.settings

sealed class ValidationResult {
    object NotChecked : ValidationResult()
    object Checking : ValidationResult()
    data class Valid(val version: String = "") : ValidationResult()
    data class Invalid(val reason: String) : ValidationResult()
}

data class SettingsUiState(
    val agyBinaryPath: String = "",
    val idleSettleDelay: Long = 100L,
    val validationResult: ValidationResult = ValidationResult.NotChecked,
    val isLoading: Boolean = true
)
