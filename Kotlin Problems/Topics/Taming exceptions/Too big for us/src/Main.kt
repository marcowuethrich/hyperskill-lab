fun returnValue(): Int = readln().toInt().also {
    if (it > 0) throw Exception("It's too big")
}
