package com.agychat.app.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

class NavigationActions(private val navController: NavController) {
    fun navigateToChatList() {
        navController.navigate(NavRoutes.ChatList.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToWorkspaceList() {
        navController.navigate(NavRoutes.WorkspaceList.route) {
            launchSingleTop = true
        }
    }

    fun navigateToSettings() {
        navController.navigate(NavRoutes.Settings.route) {
            launchSingleTop = true
        }
    }

    fun navigateToChat(sessionId: String) {
        navController.navigate(NavRoutes.Chat.createRoute(sessionId))
    }

    fun navigateToPlanViewer(sessionId: String) {
        navController.navigate(NavRoutes.PlanViewer.createRoute(sessionId))
    }

    fun navigateUp() {
        navController.navigateUp()
    }
}
