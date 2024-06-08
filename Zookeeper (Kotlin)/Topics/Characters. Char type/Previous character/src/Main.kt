fun main() {
    val chars = mutableListOf<Char>()
    for (i in 0..3) {
        chars.add(readln().first() - 1)
    }
    println(chars.joinToString("\n"))
}
