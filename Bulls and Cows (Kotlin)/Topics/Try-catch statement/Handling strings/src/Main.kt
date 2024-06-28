fun printFifthCharacter(input: String): String = try {
    "The fifth character of the entered word is ${input[4]}"
} catch (e: StringIndexOutOfBoundsException) {
    "The input word is too short!"
}
