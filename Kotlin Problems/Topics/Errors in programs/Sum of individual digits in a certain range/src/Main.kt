fun main() {

    val userInput = readln().trim().toIntOrNull()

    if (userInput == null || userInput !in 1..9999)
        println("Invalid Input")
    else
        println(userInput.toString().toList().map { it.digitToInt() }.sumOf { it })
}
