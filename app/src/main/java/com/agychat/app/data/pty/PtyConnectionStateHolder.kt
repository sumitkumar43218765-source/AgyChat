package com.agychat.app.data.pty

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class PtyConnectionStateHolder @Inject constructor() {
    private val _state = MutableStateFlow<PtyConnectionState>(PtyConnectionState.Disconnected)

    fun setState(state: PtyConnectionState) {
        _state.value = state
    }

    fun observeState(): StateFlow<PtyConnectionState> = _state.asStateFlow()

    fun currentState(): PtyConnectionState = _state.value
}
