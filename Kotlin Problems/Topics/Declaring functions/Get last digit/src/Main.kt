
fun getLastDigit(number: Int) = number.toString().last()

fun main() {
    val a = readln().toInt()
    println(getLastDigit(a))
}
