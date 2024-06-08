fun stringToIntLength(input: String): Int = try {
    val number = input.trim().toInt()
    number.toString().length
} catch (e: NumberFormatException) {
    -1
} catch (e: Exception) {
    -1
}

fun main() {
    val strInput = readln()
    println(stringToIntLength(strInput))
}
