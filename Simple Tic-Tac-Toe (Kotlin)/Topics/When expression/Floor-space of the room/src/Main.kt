import kotlin.math.pow
import kotlin.math.sqrt

fun main() = readln().let {
    when (it) {
        "triangle" -> handleTriangle()
        "rectangle" -> handleRectangle()
        "circle" -> handleCircle()
        else -> "Invalid type"
    }
}.let(::println)

fun handleTriangle(): Double {
    val a = readln().toDouble()
    val b = readln().toDouble()
    val c = readln().toDouble()
    val s = (a + b + c) / 2
    return sqrt(s * (s - a) * (s - b) * (s - c))
}

fun handleRectangle() = readln().toDouble() * readln().toDouble()
fun handleCircle() = 3.14 * readln().toDouble().pow(2)
