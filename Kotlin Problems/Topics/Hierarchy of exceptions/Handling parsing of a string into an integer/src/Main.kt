fun main() {
    println(
        try {
            readln().toInt()
            "Operation successful"
        } catch (e: NumberFormatException) {
            "Operation failed: Non-numeric string"
        } catch (e: Exception) {
            "Operation failed: Unexpected error"
        }
    )
}
