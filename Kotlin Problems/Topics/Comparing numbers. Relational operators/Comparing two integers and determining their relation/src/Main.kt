import java.util.*

fun main(args: Array<String>) {
    // Scanner object for reading inputs.
    val reader = Scanner(System.`in`)

    val a = reader.nextInt()
    val b = reader.nextInt()

    if (a == b) println("equal")
    if (a > b) println("greater")
    if (a < b) println("less")

}
