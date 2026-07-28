package com.agychat.app.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agychat.app.domain.usecase.settings.GetAgyBinaryPathUseCase
import com.agychat.app.domain.usecase.settings.GetIdleSettleDelayUseCase
import com.agychat.app.domain.usecase.settings.SetAgyBinaryPathUseCase
import com.agychat.app.domain.usecase.settings.SetIdleSettleDelayUseCase
import com.agychat.app.domain.usecase.settings.ValidateAgyInstallationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getAgyBinaryPathUseCase: GetAgyBinaryPathUseCase,
    private val setAgyBinaryPathUseCase: SetAgyBinaryPathUseCase,
    private val getIdleSettleDelayUseCase: GetIdleSettleDelayUseCase,
    private val setIdleSettleDelayUseCase: SetIdleSettleDelayUseCase,
    private val validateAgyInstallationUseCase: ValidateAgyInstallationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val path = getAgyBinaryPathUseCase().first()
                val delay = getIdleSettleDelayUseCase().first()
                _uiState.update {
                    it.copy(
                        agyBinaryPath = path,
                        idleSettleDelay = delay,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onEvent(event: SettingsUiEvent) {
        when (event) {
            is SettingsUiEvent.UpdateAgyPath -> {
                _uiState.update { it.copy(agyBinaryPath = event.path, validationResult = ValidationResult.NotChecked) }
            }
            is SettingsUiEvent.UpdateIdleSettleDelay -> {
                _uiState.update { it.copy(idleSettleDelay = event.delayMs) }
            }
            is SettingsUiEvent.ValidateInstallation -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(validationResult = ValidationResult.Checking) }
                    try {
                        val isValid = validateAgyInstallationUseCase(uiState.value.agyBinaryPath)
                        if (isValid) {
                            _uiState.update { it.copy(validationResult = ValidationResult.Valid("Valid Installation")) }
                        } else {
                            _uiState.update { it.copy(validationResult = ValidationResult.Invalid("Invalid binary path or missing execute permissions")) }
                        }
                    } catch (e: Exception) {
                        _uiState.update { it.copy(validationResult = ValidationResult.Invalid(e.localizedMessage ?: "Unknown error")) }
                    }
                }
            }
            is SettingsUiEvent.SaveSettings -> {
                viewModelScope.launch {
                    setAgyBinaryPathUseCase(uiState.value.agyBinaryPath)
                    setIdleSettleDelayUseCase(uiState.value.idleSettleDelay)
                }
            }
        }
    }
}
