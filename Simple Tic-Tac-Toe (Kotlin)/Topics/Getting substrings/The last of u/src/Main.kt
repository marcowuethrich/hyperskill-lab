fun main() {
    val string = readln()
    val text = string.substringAfterLast('u').uppercase()
    val text2 = string.substring(0, string.length - text.length)
    println(text2 + text)
}
