import com.termux.terminal.TerminalSession
import java.lang.reflect.Modifier

fun main() {
    val constructors = TerminalSession::class.java.constructors
    for (c in constructors) {
        println(c)
    }
}
