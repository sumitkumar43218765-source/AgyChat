package com.agychat.app.domain.usecase.pty

import com.agychat.app.domain.repository.PtyBridgeRepository
import javax.inject.Inject

/**
 * Use case to write raw input to the PTY.
 */
class WriteRawInputToPtyUseCase @Inject constructor(
    private val ptyRepo: PtyBridgeRepository
) {
    suspend operator fun invoke(bytes: ByteArray) {
        ptyRepo.writeInput(bytes)
    }
}
