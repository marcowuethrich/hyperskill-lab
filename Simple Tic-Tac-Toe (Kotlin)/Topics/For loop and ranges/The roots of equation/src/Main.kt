fun main() {
    val a = readln().toInt()
    val b = readln().toInt()
    val c = readln().toInt()
    val d = readln().toInt()

    for (i in 1 until 1000) {
        val result = a * i * i * i + b * i * i + c * i + d
        if (result == 0) println(i)
    }
}
