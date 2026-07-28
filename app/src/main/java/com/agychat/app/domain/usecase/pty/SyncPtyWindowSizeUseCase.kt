package com.agychat.app.domain.usecase.pty

import com.agychat.app.domain.model.PtySize
import com.agychat.app.domain.repository.PtyBridgeRepository
import javax.inject.Inject

/**
 * Use case to sync PTY window size.
 */
class SyncPtyWindowSizeUseCase @Inject constructor(
    private val ptyRepo: PtyBridgeRepository
) {
    suspend operator fun invoke(size: PtySize) {
        ptyRepo.syncWindowSize(size)
    }
}
