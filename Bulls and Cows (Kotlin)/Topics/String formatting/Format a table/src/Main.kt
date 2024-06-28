fun main() {
    val a = readln().toDouble()
    val b = readln().toDouble()
    val c = readln().toDouble()
    val d = readln().toDouble()

    val FORMAT = "%,12.2f"
    print(String.format(FORMAT, a))
    print(String.format(FORMAT, b))
    println("")
    print(String.format(FORMAT, c))
    print(String.format(FORMAT, d))
}
