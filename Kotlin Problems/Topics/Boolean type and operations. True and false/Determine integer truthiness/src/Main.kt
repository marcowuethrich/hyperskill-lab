import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    // Read an integer value from standard input
    val value = scanner.nextInt()

    // TODO: Determine if the value is 'truthy' or 'falsy'
    // - 0 is considered 'falsy'
    // - Any other value is considered 'truthy'
    val isTruthy = value != 0

    // TODO: Print 'true' if the value is truthy, or 'false' if it is falsy
    println(if (isTruthy) "true" else "false")

}
