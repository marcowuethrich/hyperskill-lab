fun main() = readln()
    .split(" ")
    .sumOf { it.trim().count() }
    .let(::println)
