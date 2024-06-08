fun main() {
    val firstList = readln().split(' ').map { it }.toMutableList()
    val secondList = readln().split(' ').map { it }.toMutableList()
    // do not touch the lines above
    println((firstList + secondList).joinToString())
}
