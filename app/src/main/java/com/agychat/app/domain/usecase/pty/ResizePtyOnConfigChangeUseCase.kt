package com.agychat.app.domain.usecase.pty

import com.agychat.app.domain.model.PtySize
import com.agychat.app.domain.repository.PtyBridgeRepository
import javax.inject.Inject

/**
 * Use case to resize PTY on configuration change.
 */
class ResizePtyOnConfigChangeUseCase @Inject constructor(
    private val ptyRepo: PtyBridgeRepository
) {
    suspend operator fun invoke(rows: Int, cols: Int) {
        ptyRepo.syncWindowSize(PtySize(rows, cols))
    }
}
