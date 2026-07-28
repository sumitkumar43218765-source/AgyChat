package com.agychat.app.domain.usecase.pty

import com.agychat.app.domain.repository.PtyBridgeRepository
import javax.inject.Inject

/**
 * Use case to stop the Agy process.
 */
class StopAgyProcessUseCase @Inject constructor(
    private val ptyRepo: PtyBridgeRepository
) {
    suspend operator fun invoke() {
        ptyRepo.stopProcess()
    }
}
