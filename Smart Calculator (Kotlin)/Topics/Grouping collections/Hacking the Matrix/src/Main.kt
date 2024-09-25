fun main() {
    val list = readln().split(" ")
    // write your code here
    val res = list.groupBy { it.length }.mapValues { (_, v) -> v.size }

    println(res)
}
