fun printIfPrime(number: Int) {
    var flag = true
    for (i in 2..number / 2) {
        if (number % i == 0) {
            flag = false
            break
        }
    }
    if (flag && number != 0 && number != 1)
        println("$number is a prime number.")
    else
        println("$number is not a prime number.")
}

fun main(args: Array<String>) {
    val number = readln().toInt()
    printIfPrime(number)
}
