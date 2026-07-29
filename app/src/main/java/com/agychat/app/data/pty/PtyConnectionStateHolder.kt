package com.agychat.app.data.pty

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import com.agychat.app.domain.model.PtyConnectionState

class PtyConnectionStateHolder @Inject constructor() {
    private val _state = MutableStateFlow<PtyConnectionState>(PtyConnectionState.DISCONNECTED)

    fun setState(state: PtyConnectionState) {
        _state.value = state
    }

    fun observeState(): StateFlow<PtyConnectionState> = _state.asStateFlow()

    fun currentState(): PtyConnectionState = _state.value
}
