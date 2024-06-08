// Import Scanner class to read user input
import java.util.*

fun main() {
    // Create a Scanner object to read user input
    val scanner = Scanner(System.`in`)

    val age: Int = scanner.nextInt()

    if (age < 18) println("You are a minor")
    if (age in 18..64) println("You are an adult")
    if (age > 64) println("You are a senior")

}
