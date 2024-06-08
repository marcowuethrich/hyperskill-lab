fun calculateSquare(number: Int?) = when (number) {
    null -> println("Input is null")
    else -> println(number * number)
}

fun main(args: Array<String>) {
    calculateSquare(readlnOrNull()?.toIntOrNull())
}
