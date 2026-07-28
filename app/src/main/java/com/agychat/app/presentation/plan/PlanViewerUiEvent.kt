package com.agychat.app.presentation.plan

sealed class PlanViewerUiEvent {
    data class SelectTab(val index: Int) : PlanViewerUiEvent()
    object RefreshArtifacts : PlanViewerUiEvent()
    object DismissError : PlanViewerUiEvent()
}
