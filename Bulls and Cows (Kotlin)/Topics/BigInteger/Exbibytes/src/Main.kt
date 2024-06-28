import java.math.BigInteger

fun main() = readln().toBigInteger()
    .multiply(BigInteger.TWO.pow(63))
    .let(::println)
