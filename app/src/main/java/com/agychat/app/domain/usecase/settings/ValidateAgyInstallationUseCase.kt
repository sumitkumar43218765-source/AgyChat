package com.agychat.app.domain.usecase.settings

import com.agychat.app.domain.repository.SettingsRepository
import javax.inject.Inject

/**
 * Use case to validate the Agy installation.
 */
class ValidateAgyInstallationUseCase @Inject constructor(
    private val repo: SettingsRepository
) {
    suspend operator fun invoke(path: String): Boolean {
        return repo.validateAgyInstallation(path)
    }
}
