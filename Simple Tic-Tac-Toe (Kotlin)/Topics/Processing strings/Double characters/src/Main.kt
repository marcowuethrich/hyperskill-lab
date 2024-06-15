fun main() {
    val input = readln()
    var output = ""
    for (i in input.indices) {
        output += input[i]
        output += input[i]
    }
    println(output)
}
