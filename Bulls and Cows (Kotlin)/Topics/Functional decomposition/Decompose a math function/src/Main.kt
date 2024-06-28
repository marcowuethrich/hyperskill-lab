fun f(x: Double): Double = when {
    f1(x) -> (x * x) + 1
    f2(x) -> 1 / (x * x)
    f3(x) -> (x * x) - 1
    else -> x
}

fun f1(x: Double) = x <= 0

fun f2(x: Double) = 0 < x && x < 1

fun f3(x: Double) = x >= 1
