package com.agychat.app.presentation.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.agychat.app.presentation.components.AgyChatButton
import com.agychat.app.presentation.components.AgyChatTopBar
import com.agychat.app.presentation.theme.AgyPrimary
import com.agychat.app.presentation.theme.AgySurfaceDark
import com.agychat.app.presentation.theme.AgyTextPrimary
import com.agychat.app.presentation.theme.Dimens

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AgyChatTopBar(
                title = "Settings",
                onNavigateBack = {
                    viewModel.onEvent(SettingsUiEvent.SaveSettings)
                    onNavigateBack()
                }
            )
        },
        containerColor = AgySurfaceDark
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = AgyPrimary)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(Dimens.PaddingNormal)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Agy Binary Configuration",
                    style = MaterialTheme.typography.titleMedium,
                    color = AgyTextPrimary
                )
                Spacer(modifier = Modifier.height(Dimens.PaddingNormal))

                AgyPathPickerField(
                    path = uiState.agyBinaryPath,
                    onPathChange = { viewModel.onEvent(SettingsUiEvent.UpdateAgyPath(it)) }
                )

                Spacer(modifier = Modifier.height(Dimens.PaddingLarge))
                Text(
                    text = "Advanced Settings",
                    style = MaterialTheme.typography.titleMedium,
                    color = AgyTextPrimary
                )
                Spacer(modifier = Modifier.height(Dimens.PaddingNormal))

                IdleSettleDelaySlider(
                    value = uiState.idleSettleDelay,
                    onValueChange = { viewModel.onEvent(SettingsUiEvent.UpdateIdleSettleDelay(it)) }
                )

                Spacer(modifier = Modifier.height(Dimens.PaddingLarge))

                ValidateInstallationSection(
                    result = uiState.validationResult,
                    onValidate = { viewModel.onEvent(SettingsUiEvent.ValidateInstallation) }
                )

                Spacer(modifier = Modifier.height(Dimens.PaddingLarge))
                
                AgyChatButton(
                    text = "Save Settings",
                    onClick = { viewModel.onEvent(SettingsUiEvent.SaveSettings) },
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}
