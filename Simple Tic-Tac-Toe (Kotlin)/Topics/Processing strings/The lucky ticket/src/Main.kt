fun main() = readln()
    .toCharArray()
    .map { it.digitToInt() }
    .let { it.slice(0 until 3).sum() == it.slice(3 until it.size).sum() }
    .let { if (it) "Lucky" else "Regular" }
    .let(::println)
