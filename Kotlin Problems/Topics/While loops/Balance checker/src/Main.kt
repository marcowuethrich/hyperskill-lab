fun main() {
    val balance = readln().toInt()
    val payments = readln().split(" ").map { it.toInt() }

    if (balance >= payments.sum()) {
        println("Thank you for choosing us to manage your account! Your balance is ${balance - payments.sum()}.")
    } else {
        var newBalance = balance
        for (it in payments) {
            if (newBalance < it) {
                println("Error, insufficient funds for the purchase. Your balance is $newBalance, but you need $it.")
                break
            }
            newBalance -= it
        }
    }
}
