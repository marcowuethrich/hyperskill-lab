import kotlin.random.Random

fun main() {
    val inputs = readln().split(" ").map { it.toInt() }
    println(generatePassword(inputs[0], inputs[1], inputs[2], inputs[3]))
}

fun generatePassword(a: Int, b: Int, c: Int, n: Int): String {
    if (a + b + c > n) throw IllegalArgumentException("Invalid inputs!")

    val characters = ('A'..'Z').toList().toCharArray()
    val lowercaseCharacters = ('a'..'z').toList().toCharArray()
    val digits = ('0'..'9').toList().toCharArray()

    var aCounter = a
    var bCounter = b
    var cCounter = c
    var lastChar: Char? = null

    fun generateRandomChar(array: CharArray): Char {
        var char: Char
        do {
            char = array[Random.nextInt(array.size)]
        } while (char == lastChar)
        lastChar = char
        return char
    }

    val password = StringBuilder()

    for (i in 0 until n) {
        when {
            aCounter > 0 -> {
                password.append(generateRandomChar(characters))
                aCounter--
            }

            bCounter > 0 -> {
                password.append(generateRandomChar(lowercaseCharacters))
                bCounter--
            }

            cCounter > 0 -> {
                password.append(generateRandomChar(digits))
                cCounter--
            }

            else -> {
                val set = arrayOf(characters, lowercaseCharacters, digits).random()
                password.append(generateRandomChar(set))
            }
        }
    }

    return password.toString()
}
