import java.util.Scanner

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    var input = scanner.nextInt()

    // Then, add five to the read number and multiply the result by two.
    input += 5
    input *= 2

    // Finally, print the result of the multiplication to the standard output.
    println(input)
}
