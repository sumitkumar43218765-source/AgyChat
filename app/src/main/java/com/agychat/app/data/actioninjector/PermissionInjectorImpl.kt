package com.agychat.app.data.actioninjector

import javax.inject.Inject

interface PtyInputWriter {
    fun write(data: ByteArray)
}

class PermissionInjectorImpl @Inject constructor(
    private val ptyInputWriter: PtyInputWriter,
    private val calculator: ArrowNavigationCalculator,
    private val sequenceBuilder: ArrowKeyEscapeSequenceBuilder
) {
    fun inject(currentIndex: Int, targetIndex: Int) {
        val delta = calculator.computeDelta(currentIndex, targetIndex)
        val sequence = sequenceBuilder.buildSequence(delta)
        ptyInputWriter.write(sequence)
    }
}
