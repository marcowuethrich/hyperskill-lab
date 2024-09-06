package calculator

import java.math.BigInteger
import java.util.*

const val HELP_TEXT = """
This calculator can perform addition (+) and subtraction (-) operations.
It supports both unary and binary minus operators. Additionally, it handles sequences of the same operators.
For instance:
1 + 1 results in 2
2 - 3 - 4 results in -5
9 +++ 10 -- 8 results in 27
3 --- 5 results in -2
If you enter /exit, the program will terminate.
"""

const val UNKNOWN_VARIABLE = "Unknown variable"
const val UNKNOWN_COMMAND = "Unknown command"
const val INVALID_EXPRESSION = "Invalid expression"
const val INVALID_ASSIGNMENT = "Invalid assignment"
const val INVALID_IDENTIFIER = "Invalid identifier"

fun main() {
    val store = mutableMapOf<String, BigInteger>()
    while (true) {
        val input = readln().trim()
        if (input.isBlank()) continue
        if (input == "/help") {
            println(HELP_TEXT)
            continue
        }
        if (input == "/exit") {
            println("Bye!")
            break
        }
        if (input.startsWith("/")) {
            println(UNKNOWN_COMMAND)
            continue
        }
        // assign variables
        if (input.contains("=")) {
            if (!containsExactlyOneEquals(input)) {
                println(INVALID_ASSIGNMENT)
                continue
            }
            if (!isValidIdentifierBeforeEquals(input)) {
                println(INVALID_IDENTIFIER)
                continue
            }
            if (!isValidRightSide(input)) {
                println(INVALID_ASSIGNMENT)
                continue
            }
            val leftSide = input.split("=")[0].trim()
            var rightSide = input.split("=")[1].trim()

            if (isLetterOrWord(rightSide)) {
                if (store.containsKey(rightSide)) {
                    rightSide = store[rightSide].toString()
                } else {
                    println(UNKNOWN_VARIABLE)
                    continue
                }
            }
            store[leftSide] = rightSide.toBigInteger()
            continue
        }
        // print variable
        if (isLetterOrWord(input)) {
            println(store[input] ?: UNKNOWN_VARIABLE)
            continue
        }
        // check if all variables in the expression are available
        if (!variablesAreExisting(input, store)) {
            println(store[input]?.let { UNKNOWN_VARIABLE })
            continue
        }

        val expression = buildExpression(input, store)
        if (!isValidExpression(expression)) {
            println(INVALID_EXPRESSION)
            continue
        }
        // calculation
        println(calculateExpression(expression))
    }
}

fun isLetterOrWord(expression: String): Boolean {
    val regex = "^[a-zA-Z]+$".toRegex()
    return regex.matches(expression)
}


fun containsExactlyOneEquals(expression: String): Boolean {
    val regex = "^[^=]*=[^=]*$".toRegex()
    return regex.matches(expression)
}

fun isValidIdentifierBeforeEquals(expression: String): Boolean {
    val regex = "^\\s*[a-zA-Z]+\\s*=".toRegex()
    return regex.containsMatchIn(expression)
}

fun isValidRightSide(expression: String): Boolean {
    val regex = "=\\s*([a-zA-Z]+|-?\\d+)\\s*$".toRegex()
    return regex.containsMatchIn(expression)
}

fun isValidExpression(expression: String): Boolean {
    // Regular expression for arithmetic expression with allowed characters
    val regex = "^[-+]?([a-zA-Z]+|\\d+)([\\d+\\-\\s*/^()]*)$".toRegex()

    // Check if the string matches the basic regex pattern
    if (!regex.matches(expression)) {
        return false
    }

    // Additional logic to ensure balanced parentheses
    var balance = 0
    for (char in expression) {
        when (char) {
            '(' -> balance++
            ')' -> balance--
        }
        // If balance ever goes negative, parentheses are not balanced
        if (balance < 0) return false
    }
    // Ensure that we have balanced parentheses
    if (balance != 0) {
        return false
    }

    // Additional logic to ensure * and / are used properly
    val invalidOperators = Regex("[*/]{2,}|[*/]=|=[*/]")
    if (invalidOperators.containsMatchIn(expression)) {
        return false
    }

    return true
}

fun variablesAreExisting(input: String, store: MutableMap<String, BigInteger>): Boolean {
    val regex = "[a-zA-Z]+".toRegex()
    val matches = regex.findAll(input)
    matches.forEach { match ->
        if (!store.containsKey(match.value)) return false
    }
    return true
}

fun buildExpression(input: String, store: MutableMap<String, BigInteger>): String {
    val regex = "[a-zA-Z]+".toRegex()
    val result = regex.replace(input) {
        val word = it.value
        if (store.containsKey(word)) store[word].toString() else word
    }
    return result
}

fun normalizeOperators(expression: String): String {
    var normalizedExp = expression

    while (normalizedExp.contains("--") || normalizedExp.contains("++")) {
        normalizedExp = normalizedExp.replace("--", "+").replace("++", "+")
    }
    while (normalizedExp.contains("-+") || normalizedExp.contains("+-")) {
        normalizedExp = normalizedExp.replace("-+", "-").replace("+-", "-")
    }

    return normalizedExp
}

fun infixToPostfix(expression: String): String {
    // expression = 8 * 3 + 12 * (4 - 2) => 8 3 * 12 4 2 - * +
    // expression = -10 => -10

    val precedence = mapOf('+' to 1, '-' to 1, '*' to 2, '/' to 2, '^' to 3)
    val stack = java.util.Stack<Char>()
    val result = StringBuilder()
    val tokens = expression.replace(" ", "").toCharArray()
    var i = 0

    while (i < tokens.size) {
        when (val char = tokens[i]) {
            in '0'..'9', '-' -> {
                val num = StringBuilder()
                if (char == '-') {
                    // Check if '-' is part of a negative number
                    if (i == 0 || tokens[i - 1] == '(' || tokens[i - 1] in precedence.keys) {
                        num.append(char)
                        i++
                    } else {
                        // It's a subtraction operator
                        while (stack.isNotEmpty() && precedence[stack.peek()] ?: -1 >= precedence[char] ?: 0) {
                            result.append(stack.pop()).append(' ')
                        }
                        stack.push(char)
                        i++
                        continue
                    }
                }
                // Collect the number
                while (i < tokens.size && tokens[i].isDigit()) {
                    num.append(tokens[i])
                    i++
                }
                result.append(num).append(' ')
                i--
            }

            '(' -> stack.push(char)
            ')' -> {
                while (stack.isNotEmpty() && stack.peek() != '(') {
                    result.append(stack.pop()).append(' ')
                }
                if (stack.isNotEmpty() && stack.peek() != '(') {
                    throw IllegalArgumentException("Invalid Expression") // unbalanced parentheses
                } else if (stack.isNotEmpty()) {
                    stack.pop()
                }
            }

            in precedence.keys -> {
                while (stack.isNotEmpty() && precedence[stack.peek()] ?: -1 >= precedence[char] ?: 0) {
                    result.append(stack.pop()).append(' ')
                }
                stack.push(char)
            }

            else -> {
                if (!char.isWhitespace()) {
                    throw IllegalArgumentException("Invalid character in expression: $char")
                }
            }
        }
        i++
    }

    while (stack.isNotEmpty()) {
        if (stack.peek() == '(') {
            throw IllegalArgumentException("Invalid Expression") // unbalanced parentheses
        }
        result.append(stack.pop()).append(' ')
    }

    return result.toString().trim()
}

fun evalPostfix(postfix: String): BigInteger {
    // postfix example = 3843+2*1+*+621+/- => 121
    // postfix example = 8 3 * 12 4 2 - * + => 48
    // postfix example = -10 => -10

    val stack = Stack<BigInteger>()
    val tokens = postfix.split(" ")

    for (token in tokens) {
        when {
            token.isNotEmpty() && token.matches("-?\\d+".toRegex()) -> stack.push(token.toBigInteger())
            token.isEmpty() -> continue // Handle extra spaces
            token.length == 1 -> {
                val operator = token[0]
                if (operator in setOf('+', '-', '*', '/')) {
                    if (stack.size < 2) {
                        throw IllegalArgumentException("Invalid postfix expression: $postfix")
                    }
                    val b = stack.pop()
                    val a = stack.pop()
                    val result = when (operator) {
                        '+' -> a.plus(b)
                        '-' -> a.minus(b)
                        '*' -> a.times(b)
                        '/' -> a.div(b)
                        else -> throw IllegalArgumentException("Invalid operator")
                    }
                    stack.push(result)
                } else {
                    throw IllegalArgumentException("Invalid token: $token")
                }
            }

            else -> throw IllegalArgumentException("Invalid token: $token")
        }
    }
    if (stack.size != 1) {
        throw IllegalArgumentException("Invalid postfix expression: $postfix")
    }
    return stack.pop()
}

fun calculateExpression(expression: String): BigInteger {
    // example expression: 3 + 8 * ((4 + 3) * 2 + 1) - 6 / (2 + 1)
    val normalizedExpression = normalizeOperators(expression)
    val postfix = infixToPostfix(normalizedExpression)
    return evalPostfix(postfix)
}
