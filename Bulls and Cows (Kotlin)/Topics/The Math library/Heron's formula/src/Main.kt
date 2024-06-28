import kotlin.math.sqrt

fun main() {
    val (a, b, c) = DoubleArray(3) { readln().toDouble() }

    val p = (a + b + c) / 2
    val area = sqrt(p * (p - a) * (p - b) * (p - c))
    println(area)
}
