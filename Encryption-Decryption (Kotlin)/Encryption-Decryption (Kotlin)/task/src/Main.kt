package encryptdecrypt

import java.io.File
import kotlin.math.abs

fun main(args: Array<String>) {
    var mode = "enc"
    var key = 0
    var dataParam = ""
    var inputFileName = ""
    var outputFileName = ""
    var alg = "shift"
    args.forEachIndexed { index, arg ->
        when (arg) {
            "-mode" -> mode = args[index + 1]
            "-key" -> key = args[index + 1].toInt()
            "-data" -> dataParam = args[index + 1]
            "-in" -> inputFileName = args[index + 1]
            "-out" -> outputFileName = args[index + 1]
            "-alg" -> alg = args[index + 1]
        }
    }
    val data = dataParam.ifBlank {
        val file = File(inputFileName)
        if (file.exists()) file.readText() else ""
    }

    fun processResultResult(block: () -> String) {
        if (outputFileName.isBlank()) {
            println(block())
        } else {
            File(outputFileName).writeText(block())
        }
    }

    when (mode) {
        "enc" -> processResultResult {
            data.encrypt(alg, key)
        }

        "dec" -> processResultResult {
            data.decryption(alg, key)
        }

        else -> throw RuntimeException("Unexpected mode $mode")
    }
}

private fun String.encrypt(alg: String, shiftAmount: Int): String =
    when (alg) {
        "shift" -> {
            val upperCaseLetters = ('A'..'Z').toList()
            val lowerCaseLetters = ('a'..'z').toList()
            val count = upperCaseLetters.size

            val encryptedText = StringBuilder()
            for (char in this) {
                if (!char.isLetter()) encryptedText.append(char)
                if (upperCaseLetters.contains(char)) {
                    val currentIndex = upperCaseLetters.indexOf(char)
                    var newIndex = currentIndex + shiftAmount
                    println("$char, $currentIndex, $newIndex")
                    if (newIndex + 1 > count) newIndex -= count
                    encryptedText.append(upperCaseLetters[newIndex])
                }
                if (lowerCaseLetters.contains(char)) {
                    val currentIndex = lowerCaseLetters.indexOf(char)
                    var newIndex = currentIndex + shiftAmount
                    if (newIndex + 1 > count) newIndex -= count
                    encryptedText.append(lowerCaseLetters[newIndex])
                }
            }
            encryptedText.toString()
        }

        "unicode" -> {
            val encryptedText = StringBuilder()
            for (char in this) {
                encryptedText.append(char.plus(shiftAmount))
            }
            encryptedText.toString()
        }

        else -> throw Exception("Invalid encryption algorithm!")
    }


private fun String.decryption(alg: String, shiftAmount: Int): String =
    when (alg) {
        "shift" -> {
            val upperCaseLetters = ('A'..'Z').toList()
            val lowerCaseLetters = ('a'..'z').toList()
            val count = upperCaseLetters.size

            val encryptedText = StringBuilder()
            for (char in this) {
                if (!char.isLetter()) encryptedText.append(char)
                if (upperCaseLetters.contains(char)) {
                    val currentIndex = upperCaseLetters.indexOf(char)
                    var newIndex = currentIndex - shiftAmount
                    println("$char, $currentIndex, $newIndex")
                    if (newIndex < 0) newIndex = count - abs(newIndex)
                    encryptedText.append(upperCaseLetters[newIndex])
                }
                if (lowerCaseLetters.contains(char)) {
                    val currentIndex = lowerCaseLetters.indexOf(char)
                    var newIndex = currentIndex - shiftAmount
                    if (newIndex < 0) newIndex = count - abs(newIndex)
                    encryptedText.append(lowerCaseLetters[newIndex])
                }
            }
            encryptedText.toString()
        }

        "unicode" -> {
            val encryptedText = StringBuilder()
            for (char in this) {
                encryptedText.append(char.minus(shiftAmount))
            }
            encryptedText.toString()
        }

        else -> throw Exception("Invalid encryption algorithm!")
    }


