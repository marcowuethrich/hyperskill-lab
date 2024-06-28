import kotlin.math.cos
import kotlin.math.sin

fun main() = readln().toDouble().let { sin(it) - cos(it) }.let(::println)
