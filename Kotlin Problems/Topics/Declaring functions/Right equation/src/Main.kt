fun isRightEquation(d1: Int, d2: Int, result: Int) = (d1 * d2) == result

fun main() {
    val a = readln().toInt()
    val b = readln().toInt()
    val c = readln().toInt()
    println(isRightEquation(a, b, c))
}
