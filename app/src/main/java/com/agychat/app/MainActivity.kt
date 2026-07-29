package com.agychat.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.agychat.app.presentation.navigation.AgyChatNavHost
import com.agychat.app.presentation.terminal.SwipeUpTerminalHandle
import com.agychat.app.presentation.terminal.TerminalBottomSheet
import com.agychat.app.presentation.terminal.TerminalSheetViewModel
import com.agychat.app.presentation.theme.AgyChatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AgyChatTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var isTerminalVisible by remember { mutableStateOf(false) }
                    val terminalViewModel: TerminalSheetViewModel = hiltViewModel()

                    Box(modifier = Modifier.fillMaxSize()) {
                        // Main app content
                        AgyChatNavHost()

                        // Swipe-up handle at bottom (always visible when terminal is hidden)
                        if (!isTerminalVisible) {
                            SwipeUpTerminalHandle(
                                onSwipeUp = { isTerminalVisible = true },
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )
                        }

                        // Terminal bottom sheet (slides up from bottom)
                        TerminalBottomSheet(
                            viewModel = terminalViewModel,
                            isVisible = isTerminalVisible,
                            onDismiss = { isTerminalVisible = false },
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }
}
