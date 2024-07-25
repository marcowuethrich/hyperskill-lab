fun main() = readln().split(" ")
    .maxBy { it }
    .let(::println)
