fun main() {
    val text = readln()
    println("${text[text.lastIndex]}${text.substring(1 until text.lastIndex)}${text[0]}")
}
