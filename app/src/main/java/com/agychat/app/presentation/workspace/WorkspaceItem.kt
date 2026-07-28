package com.agychat.app.presentation.workspace

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.agychat.app.domain.model.ProjectWorkspace
import com.agychat.app.presentation.theme.AgySurfaceElevatedDark
import com.agychat.app.presentation.theme.AgyTextPrimary
import com.agychat.app.presentation.theme.AgyTextSecondary
import com.agychat.app.presentation.theme.CardShape
import com.agychat.app.presentation.theme.Dimens
import com.agychat.app.presentation.theme.StatusConnected

@Composable
fun WorkspaceItem(
    workspace: ProjectWorkspace,
    onSelect: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.PaddingNormal, vertical = Dimens.PaddingSmall)
            .clickable(onClick = onSelect),
        shape = CardShape,
        colors = CardDefaults.cardColors(containerColor = AgySurfaceElevatedDark)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.PaddingNormal),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = workspace.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AgyTextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (workspace.isActive) {
                        Spacer(modifier = Modifier.width(Dimens.PaddingSmall))
                        Surface(
                            shape = MaterialTheme.shapes.small,
                            color = StatusConnected.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "Active",
                                color = StatusConnected,
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(horizontal = Dimens.PaddingSmall, vertical = Dimens.PaddingTiny)
                            )
                        }
                    }
                }
                Text(
                    text = workspace.path,
                    style = MaterialTheme.typography.bodySmall,
                    color = AgyTextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = Dimens.PaddingTiny)
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Workspace",
                    tint = AgyTextSecondary
                )
            }
        }
    }
}
