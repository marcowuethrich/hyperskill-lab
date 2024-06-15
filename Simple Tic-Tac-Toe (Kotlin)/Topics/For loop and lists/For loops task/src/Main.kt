fun main() {
    val unsortedList = IntArray(readln().toInt()).map { readln().toInt() }
    val numbers = readln().split(" ").map { it.toInt() }
    println(if (unsortedList.containsAll(numbers)) "YES" else "NO")
}
