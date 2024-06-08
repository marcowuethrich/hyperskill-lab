fun main() {
    val a = readln().toLong()
    val b = readln().toLong()
    val c = readln().toLong()

    if (a < b) {
        println(b < c || b == c)
    }

    if (a > b) {
        println(b > c || b == c)
    }

    if (a == b) println(true)
}
