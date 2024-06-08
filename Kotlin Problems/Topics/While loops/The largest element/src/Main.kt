fun main() {
    val numbers = mutableListOf<Int>()
    var nextNumber = -1
    while (nextNumber != 0) {
        nextNumber = readln().toInt()
        numbers.add(nextNumber)
    }
    println(numbers.max())
}
