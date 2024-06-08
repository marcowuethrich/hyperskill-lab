fun main() {
    println(if (isNumberInRanges(readln().toInt())) "True" else "False")
}

fun isNumberInRanges(number: Int) = (-15 < number && number <= 12)
        || (number in 15..16)
        || (number >= 19)
