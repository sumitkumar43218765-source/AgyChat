package com.agychat.app.domain.usecase.settings

import com.agychat.app.domain.repository.SettingsRepository
import javax.inject.Inject

/**
 * Use case to set the idle settle delay.
 */
class SetIdleSettleDelayUseCase @Inject constructor(
    private val repo: SettingsRepository
) {
    suspend operator fun invoke(delayMs: Long) {
        repo.setIdleSettleDelay(delayMs)
    }
}
