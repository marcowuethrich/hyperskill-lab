fun main() {
    val backToTheWall = readln().split(", ").map { it }.toMutableList()
    val returnedWatchman = readln()
    // do not touch the lines above
    backToTheWall += returnedWatchman
    println(backToTheWall.joinToString())
}
