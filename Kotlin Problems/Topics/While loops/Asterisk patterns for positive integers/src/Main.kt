fun main() {
    val list = mutableListOf<Int>()
    while (true) {
        val number = readln().toInt()
        if (number <= 0) break
        list.add(number)
    }
    list.forEach { println("*".repeat(it)) }
}
