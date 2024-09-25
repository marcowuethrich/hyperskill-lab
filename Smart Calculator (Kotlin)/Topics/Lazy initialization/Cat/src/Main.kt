class Cat {
    val name: String by lazy { println("I prefer to ignore it").let { callName() } }

    fun callName() = println("Input the cat name").let { readln() }
}
