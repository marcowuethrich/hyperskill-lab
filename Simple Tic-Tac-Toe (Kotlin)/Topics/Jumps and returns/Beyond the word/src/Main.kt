fun main() = readln()
    .let { input -> ('a'..'z').filter { l -> input.none { it == l } }.joinToString("") }
    .let(::println)
