fun main() = readln().toInt().let { limit ->
    when {
        limit < 0 -> println("Invalid Input")
        else -> println((0 until limit + 1).filter { it % 2 == 0 }.sumOf { it * it })
    }
}

