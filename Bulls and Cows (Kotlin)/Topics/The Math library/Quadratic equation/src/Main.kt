import kotlin.math.sqrt

fun main() = DoubleArray(3)
    .map { readln().toDouble() }
    .let {
        val (a, b, c) = it
        val discriminant = b * b - 4 * a * c

        val root1 = (-b - sqrt(discriminant)) / (2 * a)
        val root2 = (-b + sqrt(discriminant)) / (2 * a)

        if (root1 < root2) "$root1 $root2" else "$root2 $root1"
    }.let(::println)
