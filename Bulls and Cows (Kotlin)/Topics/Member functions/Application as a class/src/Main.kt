class Application(val name: String) {
    fun run(a: String, b: String, c: String) = listOf(name, a, b, c).forEach(::println)
}
