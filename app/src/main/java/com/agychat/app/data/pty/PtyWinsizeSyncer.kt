package com.agychat.app.data.pty

import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

class PtyWinsizeSyncer @Inject constructor() {
    private val currentSize = AtomicReference(PtySize(24, 80))

    fun setSize(size: PtySize) {
        currentSize.set(size)
    }

    fun getSize(): PtySize {
        return currentSize.get()
    }

    fun hasChanged(newSize: PtySize): Boolean {
        return currentSize.get() != newSize
    }
}
