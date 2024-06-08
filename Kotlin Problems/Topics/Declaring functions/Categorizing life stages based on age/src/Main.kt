fun main() {
    val age = readln().toInt()
    println(ageToLifeStage(age))
}

fun ageToLifeStage(age: Int): String = when {
    age < 18 -> "Minor"
    age > 65 -> "Senior"
    else -> "Adult"
}
