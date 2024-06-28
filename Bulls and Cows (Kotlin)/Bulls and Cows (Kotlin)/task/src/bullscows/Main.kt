package bullscows

import kotlin.math.min


fun main() {
    println("Input the length of the secret code:")
    val secretLength = readln().toIntOrNull()
    if (secretLength == null) {
        println("Error: isn't a valid number.")
        return
    }
    if (secretLength !in 1..36) {
        println("Error: can't generate a secret number with a length of $secretLength")
        return
    }
    println("Input the number of possible symbols in the code:")
    val possibleSymbols = readln().toIntOrNull()
    if (possibleSymbols == null) {
        println("Error: isn't a valid number.")
        return
    }
    if (secretLength > possibleSymbols) {
        println("Error: it's not possible to generate a code with a length of $secretLength with $possibleSymbols unique symbols.")
        return
    }
    if (possibleSymbols > 36) {
        println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).")
        return
    }
    val grander = Grander(secretLength, possibleSymbols)

    println("Okay, let's start a game!")
    var turnNumber = 1
    var grade: String
    do {
        println("Turn $turnNumber:")
        val userInput = readln().map { it }
        grade = grander.grande(userInput)
        println(grade)
        turnNumber++
    } while (!grade.contains("$secretLength bulls"))

    println("Congratulations! You guessed the secret code.")
}

class Grander(secretCodeLength: Int, possibleSymbols: Int) {
    private var secretCode: List<Char>
    private lateinit var digitRange: CharRange
    private var letterRange: CharRange? = null

    init {
        this.initSecretCodeRange(possibleSymbols)
        secretCode = generateSecretCode(secretCodeLength)
        println("The secret is prepared: ${"*".repeat(secretCodeLength)} (${digitRange.first}-${digitRange.last}${letterRange?.let { ", ${it.first}-${it.last}" } ?: ""}).")
    }

    fun grande(code: List<Char>): String {
        val bulls = findBulls(code)
        val cows = findCows(code)
        val output = StringBuilder("Grade: ")
        when {
            bulls == 0 && cows == 0 -> output.append("None. ")
            else -> {
                if (bulls == 1) output.append("$bulls bull")
                if (bulls > 1) output.append("$bulls bulls")
                if (bulls > 0 && cows > 0) output.append(" and ")
                if (cows == 1) output.append("$cows cow")
                if (cows > 1) output.append("$cows cows")
                output.append(". ")
            }
        }
        return output.toString()
    }

    private fun initSecretCodeRange(possibleSymbols: Int) {
        digitRange = ('0'..'9').toList().subList(0, min(10, possibleSymbols)).let { it.first()..it.last() }
        if (possibleSymbols > 10) {
            letterRange = ('a'..'z').toList().subList(0, min(26, possibleSymbols - 10)).let { it.first()..it.last() }
        }
    }

    private fun findBulls(codes: List<Char>): Int =
        codes.filterIndexed { index, char -> char == secretCode[index] }.size

    private fun findCows(codes: List<Char>): Int =
        codes.filterIndexed { index, char -> secretCode.contains(char) && char != secretCode[index] }.size

    private fun generateSecretCode(length: Int): List<Char> =
        listOf(
            *digitRange.toList().toTypedArray(),
            *(letterRange?.toList() ?: listOf()).toTypedArray()
        )
            .shuffled()
            .let { if (it[0] == '0') it.reversed() else it }
            .take(length)
}





