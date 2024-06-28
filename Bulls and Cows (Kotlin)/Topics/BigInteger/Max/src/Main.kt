fun main() = (1..2)
    .map { readln().toBigInteger() }
    .let { (a, b) -> a.max(b) }
    .let(::println)
