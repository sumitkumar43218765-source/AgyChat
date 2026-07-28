package com.agychat.app.domain.usecase.pty

import com.agychat.app.domain.repository.PtyBridgeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to observe PTY output.
 */
class ObservePtyOutputUseCase @Inject constructor(
    private val ptyRepo: PtyBridgeRepository
) {
    operator fun invoke(): Flow<ByteArray> {
        return ptyRepo.observeOutput()
    }
}
