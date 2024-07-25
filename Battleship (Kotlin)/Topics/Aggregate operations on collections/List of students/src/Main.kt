fun main() = readln().split(" ")
    .maxOf { it }
    .let(::println)
