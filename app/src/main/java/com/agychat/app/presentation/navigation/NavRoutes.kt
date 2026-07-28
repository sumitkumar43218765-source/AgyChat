package com.agychat.app.presentation.navigation

sealed class NavRoutes(val route: String) {
    object ChatList : NavRoutes("chat_list")
    object WorkspaceList : NavRoutes("workspace_list")
    object Settings : NavRoutes("settings")

    object Chat : NavRoutes("chat/{sessionId}") {
        fun createRoute(sessionId: String) = "chat/$sessionId"
    }

    object PlanViewer : NavRoutes("plan/{sessionId}") {
        fun createRoute(sessionId: String) = "plan/$sessionId"
    }
}
