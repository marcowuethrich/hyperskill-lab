fun main() {
    val numbers = IntArray(readln().toInt()).map { readln().toInt() }

    var isAsc = true
    for (i in 0 until numbers.size - 1) {
        if (numbers[i] > numbers[i + 1]) {
            isAsc = false
            break
        }
    }
    println(if (isAsc) "YES" else "NO")
}
