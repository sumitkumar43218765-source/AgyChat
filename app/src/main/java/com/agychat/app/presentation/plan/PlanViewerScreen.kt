package com.agychat.app.presentation.plan

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.agychat.app.presentation.components.AgyChatTopBar
import com.agychat.app.presentation.components.EmptyStateView
import com.agychat.app.presentation.theme.AgyPrimary
import com.agychat.app.presentation.theme.AgySurfaceDark
import com.agychat.app.presentation.theme.AgyTextPrimary

@Composable
fun PlanViewerScreen(
    viewModel: PlanViewerViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AgyChatTopBar(
                title = "Plan & Artifacts",
                onNavigateBack = onNavigateBack
            )
        },
        containerColor = AgySurfaceDark
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = AgyPrimary)
                }
            } else if (uiState.error != null) {
                EmptyStateView(
                    message = uiState.error ?: "Error loading artifacts",
                    onRetry = { viewModel.onEvent(PlanViewerUiEvent.RefreshArtifacts) }
                )
            } else {
                val tabs = listOf("Plan", "Walkthrough", "Task")
                TabRow(
                    selectedTabIndex = uiState.selectedTab,
                    containerColor = AgySurfaceDark,
                    contentColor = AgyTextPrimary,
                    indicator = { tabPositions ->
                        if (uiState.selectedTab < tabPositions.size) {
                            TabRowDefaults.Indicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[uiState.selectedTab]),
                                color = AgyPrimary
                            )
                        }
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = uiState.selectedTab == index,
                            onClick = { viewModel.onEvent(PlanViewerUiEvent.SelectTab(index)) },
                            text = { Text(text = title) },
                            selectedContentColor = AgyPrimary,
                            unselectedContentColor = AgyTextPrimary.copy(alpha = 0.7f)
                        )
                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    when (uiState.selectedTab) {
                        0 -> {
                            if (uiState.planArtifact != null) {
                                PlanCard(artifact = uiState.planArtifact!!)
                            } else {
                                EmptyStateView(message = "No Plan Artifact Found")
                            }
                        }
                        1 -> {
                            if (uiState.walkthroughArtifact != null) {
                                WalkthroughCard(artifact = uiState.walkthroughArtifact!!)
                            } else {
                                EmptyStateView(message = "No Walkthrough Artifact Found")
                            }
                        }
                        2 -> {
                            if (uiState.taskArtifact != null) {
                                TaskSummaryCard(artifact = uiState.taskArtifact!!)
                            } else {
                                EmptyStateView(message = "No Task Summary Found")
                            }
                        }
                    }
                }
            }
        }
    }
}
