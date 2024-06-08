import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val word = scanner.next()

    println("${word.length} repetitions of the word $word: ${word.repeat(word.length)}")
}
