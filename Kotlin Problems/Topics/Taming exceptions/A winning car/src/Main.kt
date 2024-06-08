fun findCar(): String {
    val maxSpeed = readln().toInt()
    val accTime = readln().toInt()

    if (maxSpeed < 120 || accTime < 1 || accTime > 4) {
        throw Exception("The race can't be won with this car")
    }
    return "I will definitely win!"
}
