fun isGreater(a1: Int, a2: Int, b1: Int, b2: Int) = (a1 + a2) > (b1 + b2)

fun main() {
    val number1 = readln().toInt()
    val number2 = readln().toInt()
    val number3 = readln().toInt()
    val number4 = readln().toInt()

    println(isGreater(number1, number2, number3, number4))
}
