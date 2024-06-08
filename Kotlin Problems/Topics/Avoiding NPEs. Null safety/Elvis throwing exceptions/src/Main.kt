fun main() {
    readLine().apply {
        if (this.isNullOrEmpty()) throw IllegalStateException()
        println("Elvis says: $this")
    }
}
