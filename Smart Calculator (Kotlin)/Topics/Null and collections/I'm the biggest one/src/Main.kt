fun main() = readln().split(" ")
    .filter { it.toIntOrNull() != null }
    .maxByOrNull { it.toInt() }
    .let(::println)
