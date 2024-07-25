fun main() {
    val poet: String = readln()

    var numberOfA = 0
    val coolScope = poet
        .also { numberOfA = poet.lowercase().count { it == 'a' } }
        .run { "Our poet is $this" }

    println(coolScope)
    println(numberOfA)
}
