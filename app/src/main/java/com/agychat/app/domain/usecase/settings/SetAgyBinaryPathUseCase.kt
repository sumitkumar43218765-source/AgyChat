package com.agychat.app.domain.usecase.settings

import com.agychat.app.domain.repository.SettingsRepository
import javax.inject.Inject

/**
 * Use case to set the Agy binary path.
 */
class SetAgyBinaryPathUseCase @Inject constructor(
    private val repo: SettingsRepository
) {
    suspend operator fun invoke(path: String) {
        repo.setAgyBinaryPath(path)
    }
}
