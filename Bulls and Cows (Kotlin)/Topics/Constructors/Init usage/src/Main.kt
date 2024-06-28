fun main() {
    val timerValue = readln().toInt()
    val timer = ByteTimer(timerValue)
    println(timer.time)
}

class ByteTimer(var time: Int) {
    init {
        if (time < -128) time = -128
        if (time > 127) time = 127
    }
}

class Worker(position: String, initialSalary: Int) {
    val position = position
    var salary = initialSalary

    init {
        if (position == "head of department") salary *= 2
    }
}
