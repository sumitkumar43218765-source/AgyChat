package com.agychat.app.data.pty

import javax.inject.Inject

class PtySigwinchListener @Inject constructor() {
    private var sizeCallback: ((PtySize) -> Unit)? = null

    fun onSizeChanged(callback: (PtySize) -> Unit) {
        this.sizeCallback = callback
    }

    fun computeSizeFromDensity(widthPx: Int, heightPx: Int, fontSizeSp: Float): PtySize {
        // Basic stub implementation
        val charWidth = fontSizeSp * 0.6f
        val charHeight = fontSizeSp * 1.2f
        val cols = (widthPx / charWidth).toInt().coerceAtLeast(1)
        val rows = (heightPx / charHeight).toInt().coerceAtLeast(1)
        return PtySize(rows, cols)
    }
    
    fun triggerSizeChange(newSize: PtySize) {
        sizeCallback?.invoke(newSize)
    }
}
