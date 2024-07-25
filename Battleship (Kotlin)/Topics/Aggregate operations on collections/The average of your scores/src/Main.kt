fun main() = readln()
    .split(" ")
    .map { it.toDouble() }
    .average()
    .let(::println)
