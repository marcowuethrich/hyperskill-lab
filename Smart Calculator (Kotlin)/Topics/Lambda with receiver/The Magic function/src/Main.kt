fun String.magic(init: String.() -> String): String {
    return init()
}

fun main() {
    val str = readln()
    val res = str.magic {
        uppercase().reversed().dropLast(1)
    }
    println(res)
}
