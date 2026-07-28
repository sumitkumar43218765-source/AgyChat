package com.agychat.app.domain.model

/**
 * Represents the connection state of a PTY (Pseudo-terminal).
 */
sealed class PtyConnectionState {
    data object DISCONNECTED : PtyConnectionState()
    data object CONNECTING : PtyConnectionState()
    data object CONNECTED : PtyConnectionState()
    data class ERROR(val message: String) : PtyConnectionState()
}
