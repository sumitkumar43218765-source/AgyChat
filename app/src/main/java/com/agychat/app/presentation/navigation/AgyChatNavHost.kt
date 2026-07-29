package com.agychat.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.agychat.app.presentation.chatlist.ChatListScreen
import com.agychat.app.presentation.chat.ChatScreen
import com.agychat.app.presentation.plan.PlanViewerScreen
import com.agychat.app.presentation.workspace.WorkspaceListScreen
import com.agychat.app.presentation.settings.SettingsScreen

@Composable
fun AgyChatNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavRoutes.ChatList.route
) {
    val navActions = remember(navController) { NavigationActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(NavRoutes.ChatList.route) {
            ChatListScreen(
                viewModel = hiltViewModel(),
                onNavigateToChat = { id -> navActions.navigateToChat(id) },
                onNavigateToSettings = { navActions.navigateToSettings() },
                onNavigateToWorkspaces = { navActions.navigateToWorkspaceList() }
            )
        }
        
        composable(NavRoutes.WorkspaceList.route) {
            WorkspaceListScreen(
                viewModel = hiltViewModel(),
                onNavigateBack = { navActions.navigateUp() }
            )
        }

        composable(NavRoutes.Settings.route) {
            SettingsScreen(
                viewModel = hiltViewModel(),
                onNavigateBack = { navActions.navigateUp() }
            )
        }

        composable(NavRoutes.Chat.route) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getString("sessionId") ?: ""
            ChatScreen(
                viewModel = hiltViewModel(),
                onNavigateBack = { navActions.navigateUp() },
                onNavigateToPlan = { navActions.navigateToPlanViewer(sessionId) }
            )
        }

        composable(NavRoutes.PlanViewer.route) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getString("sessionId") ?: ""
            PlanViewerScreen(
                viewModel = hiltViewModel(),
                onNavigateBack = { navActions.navigateUp() }
            )
        }
    }
}
