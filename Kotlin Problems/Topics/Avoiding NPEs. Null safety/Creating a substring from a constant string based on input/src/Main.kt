fun main() {
    val lengthOfSubstring = readln().toInt()
    val str = "Learning Kotlin"

    println(
        if (lengthOfSubstring > str.length)
            "Out of range"
        else
            str.substring(0, lengthOfSubstring)
    )
}
