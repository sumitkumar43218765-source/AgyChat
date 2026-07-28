package com.agychat.app.domain.usecase.pty

import com.agychat.app.domain.model.PtySize
import com.agychat.app.domain.repository.PtyBridgeRepository
import com.agychat.app.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Use case to start the Agy process.
 */
class StartAgyProcessUseCase @Inject constructor(
    private val ptyRepo: PtyBridgeRepository,
    private val settingsRepo: SettingsRepository
) {
    suspend operator fun invoke(cwd: String, conversationUuid: String?, size: PtySize): Boolean {
        val agyBinary = settingsRepo.getAgyBinaryPath().first()
        val args = mutableListOf<String>()
        if (conversationUuid != null) {
            args.add("--resume")
            args.add(conversationUuid)
        }
        return ptyRepo.startProcess(agyBinary, args, cwd, size)
    }
}
