/**
 * Program to convert Celsius to Fahrenheit
 */

fun main() {
    // Reads the next integer
    val celsius = readLine()!!.toInt()

    // calculates the celsius into fahrenheit
    val fahrenheit = (celsius * 9/5) + 32

    // prints out the computes fahrenheit value
    println(fahrenheit)
}
