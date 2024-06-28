fun main() = (1..2)
    .map { readln().toBigInteger() }
    .let { (a, b) -> (a.multiply(b)) / (a.gcd(b)) }
    .let(::println)
