fun main() {
    try {
        println(readln().toInt() / readln().toInt())
    } catch (e: ArithmeticException) {
        println("Division by zero, please fix the second argument!")
    }
}
