fun main() {
    val given = IntArray(readln().toInt()).map { readln().toInt() }
    val shifted = mutableListOf(given[given.lastIndex])
    for (i in 0 until given.lastIndex) {
        shifted.add(given[i])
    }
    println(shifted.joinToString(" "))
}
