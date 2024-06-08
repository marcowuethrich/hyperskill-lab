import java.util.*

fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)

    // input temperature in Fahrenheit
    val temperature: Int = scan.nextLine().trim().toInt()

    when {
        temperature > 85 -> println("High temperature")
        temperature < 50 -> println("Low temperature")
        else -> println("Normal temperature")
    }

}
