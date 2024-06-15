fun main() {
    val input = mutableListOf<String>()
    repeat(3) {
        input.add(readln())
    }
    println(input[0] + input[1] == input[2])
}
