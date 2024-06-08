// Importing necessary libraries
import java.util.Scanner

fun main(args: Array<String>) {
    // Initialisation of a Scanner
    val scanner = Scanner(System.`in`)

    // Get input string line
    val input = scanner.nextLine()
    // Use split to create an array
    val split = input.split(" ").map { it.toInt() }

    // Iterate over array to convert from String to Int and accumulate the sum
    val sum = split.reduce { acc, s -> acc + s }
    // Print the sum.
    println(sum)
}
