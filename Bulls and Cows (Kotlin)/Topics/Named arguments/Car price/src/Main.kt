val YEARS = 5
val KILOMETERS = 100_000
val MAX_SPEED = 120
val AUTOMATIC = false
val STARTING_PRICE = 20_000

fun carPrice(
    old: Int = YEARS,
    kilometers: Int = KILOMETERS,
    maximumSpeed: Int = MAX_SPEED,
    automatic: Boolean = AUTOMATIC
) {
    var price = STARTING_PRICE
    price -= old * 2_000
    price += (maximumSpeed - MAX_SPEED) * 100
    price -= (kilometers / 10_000) * 200
    if (automatic) price += 1500
    println(price)
}
