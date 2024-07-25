open class Animal {
    open fun makeSound() {
        println("Animal sound")
    }
}

val animal: Animal = object : Animal() {
    override fun makeSound() {
        println("Meow")
    }
}
