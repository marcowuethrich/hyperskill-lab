val identity: (Int) -> Int = { i1: Int -> i1 }
val half: (Int) -> Int = { i1: Int -> i1 / 2 }
val zero = { _: Int -> 0 }

fun generate(functionName: String): (Int) -> Int =
    when (functionName) {
        "identity" -> identity
        "half" -> half
        "zero" -> zero
        else -> zero
    }
