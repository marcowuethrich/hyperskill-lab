fun main() = IntArray(4)
    .map { readln().toBigInteger() }
    .let { (a, b, c, d) -> (-a) * b + c - d }
    .let(::println)
