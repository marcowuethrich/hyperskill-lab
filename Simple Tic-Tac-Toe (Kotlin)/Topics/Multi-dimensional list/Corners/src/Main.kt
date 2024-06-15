fun main() {
    // Do not touch code below
    val inputList: MutableList<MutableList<String>> = mutableListOf()
    val n = readln().toInt()
    for (i in 0 until n) {
        val strings = readln().split(' ').toMutableList()
        inputList.add(strings)
    }
    println("${inputList.first()[0]} ${inputList.first()[inputList.lastIndex]}")
    println("${inputList.last()[0]} ${inputList.last()[inputList.lastIndex]}")
}
