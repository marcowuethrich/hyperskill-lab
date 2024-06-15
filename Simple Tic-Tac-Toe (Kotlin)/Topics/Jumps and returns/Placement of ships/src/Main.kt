fun main() {
    val rows = mutableListOf(1, 2, 3, 4, 5)
    val columns = mutableListOf(1, 2, 3, 4, 5)

    IntArray(3)
        .map { readln().split(" ").map { it.toInt() } }
        .forEach {
            rows.remove(it[0])
            columns.remove(it[1])
        }
    println(rows.joinToString(" "))
    println(columns.joinToString(" "))
}
