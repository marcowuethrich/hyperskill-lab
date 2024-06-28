import kotlin.math.pow

fun main() = DoubleArray(2)
    .map { readln().toDouble() }
    .let { it[0].pow(it[1]) }
    .let(::println)
