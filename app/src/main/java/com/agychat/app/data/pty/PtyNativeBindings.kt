package com.agychat.app.data.pty

import com.agychat.app.domain.model.PtySize

interface PtyNativeBindings {
    fun openPty(): Int
    fun closePty(fd: Int)
    fun setWindowSize(fd: Int, rows: Int, cols: Int)
    fun getWindowSize(fd: Int): PtySize
}

class PtyNativeBindingsStub : PtyNativeBindings {
    override fun openPty(): Int = -1
    override fun closePty(fd: Int) {}
    override fun setWindowSize(fd: Int, rows: Int, cols: Int) {}
    override fun getWindowSize(fd: Int): PtySize = PtySize(24, 80)
}
