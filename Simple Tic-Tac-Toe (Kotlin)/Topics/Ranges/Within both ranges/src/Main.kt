fun main() {
    val range1 = readln().toInt()..readln().toInt()
    val range2 = readln().toInt()..readln().toInt()
    val match = readln().toInt()
    println(match in range1 && match in range2)
}
