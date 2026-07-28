package com.agychat.app.domain.usecase.permission

import com.agychat.app.domain.repository.PtyBridgeRepository
import javax.inject.Inject

/**
 * Use case to send permission response by writing arrow keys and enter to PTY.
 */
class SendPermissionResponseUseCase @Inject constructor(
    private val ptyRepo: PtyBridgeRepository
) {
    suspend operator fun invoke(currentIndex: Int, targetIndex: Int) {
        val delta = targetIndex - currentIndex
        // Placeholder for real logic to write sequences
        ptyRepo.writeInput(byteArrayOf(13)) // Enter
    }
}
