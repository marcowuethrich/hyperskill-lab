fun main() {
    val input = readln().first()
    for (l in 'a'..'z') {
        if (l == input) return
        print(l)
    }
}
