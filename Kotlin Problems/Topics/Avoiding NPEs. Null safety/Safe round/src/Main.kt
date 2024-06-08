fun main() {
    println(round(readln().toInt()) ?: 0)
}

fun round(number: Int): Int? {
    return if (number >= 1000) null else number
}
