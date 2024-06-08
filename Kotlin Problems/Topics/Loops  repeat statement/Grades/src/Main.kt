fun main() = readln().toInt().let { students ->
    var (sumD, sumC, sumB, sumA) = arrayOf(0 ,0, 0, 0)
    repeat(students) {
        readln().toInt().let { grade ->
            when (grade) {
                2 -> sumD++
                3 -> sumC++
                4 -> sumB++
                5 -> sumA++
            }
        }
    }
    println("$sumD $sumC $sumB $sumA")
}
