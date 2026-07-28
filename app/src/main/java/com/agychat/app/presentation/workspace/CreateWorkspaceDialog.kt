package com.agychat.app.presentation.workspace

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.agychat.app.presentation.components.AgyChatTextField
import com.agychat.app.presentation.theme.AgyPrimary
import com.agychat.app.presentation.theme.AgySurfaceElevatedDark
import com.agychat.app.presentation.theme.AgyTextPrimary
import com.agychat.app.presentation.theme.Dimens

@Composable
fun CreateWorkspaceDialog(
    onDismiss: () -> Unit,
    onCreate: (name: String, path: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var path by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = AgySurfaceElevatedDark,
        title = {
            Text(text = "Create Workspace", color = AgyTextPrimary)
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                AgyChatTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Workspace Name",
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(Dimens.PaddingNormal))
                AgyChatTextField(
                    value = path,
                    onValueChange = { path = it },
                    label = "Workspace Path",
                    placeholder = "/storage/emulated/0/project",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank() && path.isNotBlank()) {
                        onCreate(name, path)
                    }
                }
            ) {
                Text("Create", color = AgyPrimary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = AgyTextPrimary.copy(alpha = 0.7f))
            }
        }
    )
}
