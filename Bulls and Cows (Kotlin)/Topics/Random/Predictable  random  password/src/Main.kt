import kotlin.random.Random

fun generatePredictablePassword(seed: Int): String {
    var randomPassword = ""
    val random = Random(seed)
    val charPool: IntRange = (33..126)
    repeat(10) {
        randomPassword += charPool.random(random).toChar()
    }
    return randomPassword
}
