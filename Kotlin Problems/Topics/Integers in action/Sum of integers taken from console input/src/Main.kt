import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)

    val count = scanner.nextInt()

    var sum = 0

    for (i in 0 until count) {
        sum += scanner.nextInt()
    }

    println(sum)
}
