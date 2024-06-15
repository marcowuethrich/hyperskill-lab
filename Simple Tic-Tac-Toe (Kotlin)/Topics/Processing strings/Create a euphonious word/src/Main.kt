val VOWELS = charArrayOf('a', 'e', 'i', 'o', 'u', 'y')
val CONSONANTS = ('a'..'z').toList().toCharArray().filter { VOWELS.none { vowel -> vowel == it } }

fun Char.isVowel(): Boolean = VOWELS.any { it == this }
fun Char.isConsonant(): Boolean = CONSONANTS.any { it == this }

fun main() {
    var charsToAdd = 0
    var vowelCounter = 0
    var consonantCounter = 0

    readln().let { input ->
        for (index in input.indices) {
            val char = input[index]
            val isVowel = char.isVowel()
            val isConsonant = char.isConsonant()

            if (consonantCounter != 0 && isVowel) consonantCounter = 0
            if (vowelCounter != 0 && isConsonant) vowelCounter = 0

            if (char.isVowel()) vowelCounter++
            if (char.isConsonant()) consonantCounter++

            if (vowelCounter == 3) {
                charsToAdd += 1
                vowelCounter = 1
            }
            if (consonantCounter == 3) {
                charsToAdd += 1
                consonantCounter = 1
            }

        }
    }
    println(charsToAdd)
}

