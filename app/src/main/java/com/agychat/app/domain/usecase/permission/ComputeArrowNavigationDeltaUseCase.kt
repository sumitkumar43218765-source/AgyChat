package com.agychat.app.domain.usecase.permission

import javax.inject.Inject

/**
 * Use case to compute arrow navigation delta.
 */
class ComputeArrowNavigationDeltaUseCase @Inject constructor() {
    operator fun invoke(currentIndex: Int, targetIndex: Int): Int {
        return targetIndex - currentIndex
    }
}
