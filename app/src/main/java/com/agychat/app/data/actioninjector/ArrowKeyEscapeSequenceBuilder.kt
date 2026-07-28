package com.agychat.app.data.actioninjector

import com.agychat.app.util.AnsiConstants
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlin.math.abs

class ArrowKeyEscapeSequenceBuilder @Inject constructor() {
    fun buildSequence(delta: Int): ByteArray {
        val out = ByteArrayOutputStream()
        val count = abs(delta)
        val arrowBytes = if (delta > 0) AnsiConstants.ARROW_DOWN.toByteArray() else AnsiConstants.ARROW_UP.toByteArray()
        
        for (i in 0 until count) {
            out.write(arrowBytes)
        }
        out.write(AnsiConstants.ENTER.toByteArray())
        return out.toByteArray()
    }
}
