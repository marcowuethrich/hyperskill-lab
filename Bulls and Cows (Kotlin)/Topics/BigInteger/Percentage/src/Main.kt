import java.math.BigInteger

fun main() = (1..2)
    .map { readln().toBigInteger() }
    .let { (a, b) ->
        val sum = a.add(b)
        fun calculatePercentage(a: BigInteger, sum: BigInteger) = a * (100).toBigInteger() / (sum)
        "${calculatePercentage(a, sum)}% ${calculatePercentage(b, sum)}%"
    }
    .let(::println)
