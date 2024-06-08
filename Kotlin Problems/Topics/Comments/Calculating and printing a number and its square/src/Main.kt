// First, let's import the Scanner library from Java util package
// It is used for reading user input from the keyboard (System.in)
import java.util.Scanner

fun main(args: Array<String>) {
    // Creates an instance of Scanner
    val reader = Scanner(System.`in`)

    // Takes an integer as input
    // `nextInt()` is a method of the Scanner class that reads the next Integer token from the keyboard input
    val number = reader.nextInt()

    // Calculates the square of entered number
    // In Kotlin, we use `*` operator for multiplication.
    val square = number * number

    // Prints out the given number and its square using print statement 
    // `println()` function prints the given message and line feed (new line) in a single statement
    // An example of formatted string output is shown below for your reference.
    // Replace it with the actual print statement to display the number and its square
    println("Number: $number\nSquare: $square")
}
