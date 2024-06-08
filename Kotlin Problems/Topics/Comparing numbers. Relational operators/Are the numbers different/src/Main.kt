fun main() {
    val a = readln().toLong()
    val b = readln().toLong()
    val c = readln().toLong()

    var different = true
    if (a == b) different = false
    if (b == c) different = false
    if (a == c) different = false

    println(different)
}
