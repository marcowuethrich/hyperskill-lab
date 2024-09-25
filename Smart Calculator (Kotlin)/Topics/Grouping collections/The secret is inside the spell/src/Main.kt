/* Do not change code below */
fun main() {
    val list = readln().split(" ")
    // write your code here

    val res = list.groupBy { it.first() }.mapValues { (_, spells) -> spells.sumOf { it.length } }

    println(res)
}
