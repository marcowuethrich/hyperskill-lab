package machine

fun main() = CoffeeMachine(400, 540, 120, 9, 550).run()

enum class CoffeeType(val water: Int, val milk: Int, val beans: Int, val cost: Int) {
    ESPRESSO(250, 0, 16, 4),
    LATTE(350, 75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6)
}

class CoffeeMachine(
    private var water: Int,
    private var milk: Int,
    private var beans: Int,
    private var disposableCups: Int,
    private var money: Int
) {
    fun run() {
        var keepRunning = true
        do {
            println("Write action (buy, fill, take, remaining, exit):")
            when (readlnOrNull()) {
                "buy" -> buy()
                "fill" -> fill()
                "take" -> take()
                "remaining" -> printState()
                "exit" -> keepRunning = false
            }
        } while (keepRunning)
    }

    private fun printState() = """
        The coffee machine has:
        $water ml of water
        $milk ml of milk
        $beans g of coffee beans
        $disposableCups disposable cups
        ${'$'}$money of money
    """.trimIndent().let(::println)

    private fun take() = println("I gave you \$$money").also { money = 0 }

    private fun fill() {
        fun requestResource(resource: String) =
            println("Write how many ml of $resource you want to add:").let { readln().toInt() }
        water += requestResource("water")
        milk += requestResource("milk")
        beans += requestResource("beans")
        disposableCups += requestResource("disposable cups")
    }

    private fun buy() {
        println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
        readln().let {
            when (it) {
                "1" -> makeCoffee(CoffeeType.ESPRESSO)
                "2" -> makeCoffee(CoffeeType.LATTE)
                "3" -> makeCoffee(CoffeeType.CAPPUCCINO)
                "back" -> return
                else -> println("Option is not available!")
            }
        }
    }

    private fun makeCoffee(coffee: CoffeeType) = when {
        disposableCups <= 0 -> println("Sorry, no disposable cups left!")
        water < coffee.water -> println("Sorry, not enough water!")
        water < coffee.milk -> println("Sorry, not enough milk!")
        water < coffee.beans -> println("Sorry, not enough beans!")
        else -> {
            println("I have enough resources, making you a coffee!")
            disposableCups -= 1
            water -= coffee.water
            milk -= coffee.milk
            beans -= coffee.beans
            money += coffee.cost
        }
    }
}
