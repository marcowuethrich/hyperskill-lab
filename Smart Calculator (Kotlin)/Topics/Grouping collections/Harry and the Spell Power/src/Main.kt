data class Spell(val name: String, val power: Int)

fun main() {
    val input = readln().split(" ")
    val spells = input.map { Spell(it.split("-")[0], it.split("-")[1].toInt()) }

    // write your code here
    val res = spells.groupBy { it.power }.mapValues { (_, spells) -> spells.map { it } }

    println(res)
}
