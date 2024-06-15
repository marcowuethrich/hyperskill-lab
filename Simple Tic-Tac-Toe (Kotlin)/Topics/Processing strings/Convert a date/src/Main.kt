fun main() = readln().split("-").let {
    println(listOf(it[1], it[2], it[0]).joinToString("/"))
}
