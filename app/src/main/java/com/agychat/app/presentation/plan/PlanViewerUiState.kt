package com.agychat.app.presentation.plan

import com.agychat.app.domain.model.PlanArtifact
import com.agychat.app.domain.model.TaskArtifact
import com.agychat.app.domain.model.WalkthroughArtifact

data class PlanViewerUiState(
    val taskArtifact: TaskArtifact? = null,
    val planArtifact: PlanArtifact? = null,
    val walkthroughArtifact: WalkthroughArtifact? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val selectedTab: Int = 0 // 0=Plan, 1=Walkthrough, 2=Task
)
