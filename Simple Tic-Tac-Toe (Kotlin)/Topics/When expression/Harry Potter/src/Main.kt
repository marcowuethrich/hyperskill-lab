val HOUSE_OPTIONS = mapOf(
    "gryffindor" to "bravery",
    "hufflepuff" to "loyalty",
    "slytherin" to "cunning",
    "ravenclaw" to "intellect"
)

fun main() = readln().let { HOUSE_OPTIONS[it] ?: "not a valid house" }.let(::println)
