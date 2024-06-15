fun main() = readln().let { it.first { it in '0'..'9' } }.let(::println)
