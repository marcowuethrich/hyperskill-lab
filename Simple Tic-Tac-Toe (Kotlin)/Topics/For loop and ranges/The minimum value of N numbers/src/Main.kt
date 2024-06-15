fun main() {
    var smallestNumber: Int? = null
    for (i in 1..readln().toInt()) {
        readln().toInt().let {
            if (smallestNumber == null || smallestNumber!! > it) smallestNumber = it
        }
    }
    println(smallestNumber)
}
