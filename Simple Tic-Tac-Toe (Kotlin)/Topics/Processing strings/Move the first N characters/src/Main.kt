fun main() {
    val input = readln().split(" ")
    val s = input[0]
    val n = input[1].toInt()

    if (n >= s.length) {
        println(s)
    } else {
        println("${s.substring(n)}${s.substring(0, n)}")
    }
}
