fun main() = readln().split(" ").map { it.toInt() }
    .elementAtOrNull(readln().toInt())
    ?.let(::println)
    ?: println(null)
