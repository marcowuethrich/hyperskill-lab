data class Musician(var name: String = "", var band: String = "", var role: String = "")

fun main() {
    val musician = Musician()

    musician.apply {
        name = readln()
        band = readln()
        role = readln()
    }

    with(musician) {
        println("Name: $name")
        println("Band: $band")
        println("Role: $role")
    }
}
