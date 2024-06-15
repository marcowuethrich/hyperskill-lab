fun main() = IntArray(3).map { readln() }.let {
    when (it[1]) {
        "equals" -> it[0] == it[2]
        "plus" -> it[0] + it[2]
        "endsWith" -> it[0].endsWith(it[2])
        else -> "Unknown operation"
    }
}.let(::println)
