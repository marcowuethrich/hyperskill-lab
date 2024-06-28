fun intDivision(x: String, y: String): Int = try {
    x.toInt() / y.toInt()
} catch (e: NumberFormatException) {
    println("Read values are not integers.")
    0
} catch (e: ArithmeticException) {
    println("Exception: division by zero!")
    0
}

fun main() = println(intDivision(readln(), readln()))
