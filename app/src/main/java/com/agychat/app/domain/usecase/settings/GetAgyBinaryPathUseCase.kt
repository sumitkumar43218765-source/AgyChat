package com.agychat.app.domain.usecase.settings

import com.agychat.app.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to get the Agy binary path.
 */
class GetAgyBinaryPathUseCase @Inject constructor(
    private val repo: SettingsRepository
) {
    operator fun invoke(): Flow<String> {
        return repo.getAgyBinaryPath()
    }
}
