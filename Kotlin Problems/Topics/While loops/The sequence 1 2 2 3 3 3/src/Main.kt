fun main() {
    val outputLength = readln().toInt()
    val result = mutableListOf<Int>()
    var counter = 1

    while (result.size < outputLength) {
        repeat(counter.coerceAtLeast(result.size - outputLength)) {
            result.add(counter)
        }
        counter++
    }
    println(result.subList(0, outputLength).joinToString(separator = " "))
}
