data class Musician(var name: String = "", var band: String = "", var role: String = "")

fun main() {
    val musician = Musician()

    musician.apply {
        name = readln()
        band = readln()
        role = readln()
    }

    val num = musician
        .let {
            println(it)
            it.name.length / 4
        }
        .also { println(it) }
        .let { it + 10 }

    println(num)
}
