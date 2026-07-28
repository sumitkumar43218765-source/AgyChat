package com.agychat.app.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.agychat.app.presentation.common.AgyChatButton
import com.agychat.app.presentation.theme.AgyPrimary
import com.agychat.app.presentation.theme.AgyTextPrimary
import com.agychat.app.presentation.theme.Dimens
import com.agychat.app.presentation.theme.StatusConnected
import com.agychat.app.presentation.theme.StatusDisconnected

@Composable
fun ValidateInstallationSection(
    result: ValidationResult,
    onValidate: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Validate Installation",
            style = MaterialTheme.typography.titleMedium,
            color = AgyTextPrimary
        )
        Spacer(modifier = Modifier.height(Dimens.PaddingNormal))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AgyChatButton(
                text = "Validate",
                onClick = onValidate,
                enabled = result !is ValidationResult.Checking
            )
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                when (result) {
                    is ValidationResult.Checking -> {
                        CircularProgressIndicator(
                            color = AgyPrimary,
                            modifier = Modifier.size(Dimens.IconSizeMedium)
                        )
                        Spacer(modifier = Modifier.width(Dimens.PaddingSmall))
                        Text(text = "Checking...", color = AgyTextPrimary)
                    }
                    is ValidationResult.Valid -> {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Valid", tint = StatusConnected)
                        Spacer(modifier = Modifier.width(Dimens.PaddingSmall))
                        Text(text = "Valid", color = StatusConnected)
                    }
                    is ValidationResult.Invalid -> {
                        Icon(imageVector = Icons.Default.Error, contentDescription = "Invalid", tint = StatusDisconnected)
                        Spacer(modifier = Modifier.width(Dimens.PaddingSmall))
                        Text(text = "Invalid: ${result.reason}", color = StatusDisconnected, style = MaterialTheme.typography.bodySmall)
                    }
                    is ValidationResult.NotChecked -> {
                        Text(text = "Not checked", color = AgyTextPrimary.copy(alpha = 0.5f))
                    }
                }
            }
        }
    }
}
