fun filterAndMatch(): Map<String, Int> = readln()
    .split(" ")
    .also { println(it.size) }
    .let { urls ->
        mutableMapOf<String, Int>().apply {
            urls.forEach { this[it.lowercase()] = it.length }
        }
    }
