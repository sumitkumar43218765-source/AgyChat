package com.agychat.app.domain.usecase.pty

import com.agychat.app.domain.model.PtyConnectionState
import com.agychat.app.domain.repository.PtyBridgeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to observe PTY connection state.
 */
class ObservePtyConnectionStateUseCase @Inject constructor(
    private val ptyRepo: PtyBridgeRepository
) {
    operator fun invoke(): Flow<PtyConnectionState> {
        return ptyRepo.observeConnectionState()
    }
}
