// Start of the program
fun main(args: Array<String>) {
    /* The program reads an integer from the input. 
     * Input a single integer.
     */
    val x = readLine()!!.toInt()

    // Create a read-only (val) variable that equals twice of the input
    val doubleInput = x * 2

    // Print the value of the variable
    println(doubleInput)
}
