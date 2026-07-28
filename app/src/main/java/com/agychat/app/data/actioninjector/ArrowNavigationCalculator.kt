package com.agychat.app.data.actioninjector

import javax.inject.Inject

class ArrowNavigationCalculator @Inject constructor() {
    fun computeDelta(currentIndex: Int, targetIndex: Int): Int {
        return targetIndex - currentIndex
    }
}
