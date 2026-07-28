package com.agychat.app.presentation.workspace

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.agychat.app.presentation.common.AgyChatTopBar
import com.agychat.app.presentation.common.EmptyStateView
import com.agychat.app.presentation.theme.AgyPrimary
import com.agychat.app.presentation.theme.AgySurfaceDark
import com.agychat.app.presentation.theme.AgyTextPrimary

@Composable
fun WorkspaceListScreen(
    viewModel: WorkspaceListViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AgyChatTopBar(
                title = "Workspaces",
                onNavigateBack = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(WorkspaceListUiEvent.ShowCreateDialog) },
                containerColor = AgyPrimary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Workspace", tint = AgySurfaceDark)
            }
        },
        containerColor = AgySurfaceDark
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    color = AgyPrimary,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (uiState.workspaces.isEmpty()) {
                EmptyStateView(message = "No workspaces available. Create one to get started.")
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(uiState.workspaces) { workspace ->
                        WorkspaceItem(
                            workspace = workspace,
                            onSelect = { viewModel.onEvent(WorkspaceListUiEvent.SelectWorkspace(workspace.id)) },
                            onDelete = { viewModel.onEvent(WorkspaceListUiEvent.DeleteWorkspace(workspace.id)) }
                        )
                    }
                }
            }

            if (uiState.showCreateDialog) {
                CreateWorkspaceDialog(
                    onDismiss = { viewModel.onEvent(WorkspaceListUiEvent.DismissCreateDialog) },
                    onCreate = { name, path -> viewModel.onEvent(WorkspaceListUiEvent.CreateWorkspace(name, path)) }
                )
            }
        }
    }
}
