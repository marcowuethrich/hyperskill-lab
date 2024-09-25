data class Person(val name: String, val age: Int)

class PersonBuilder {
    var name: String = ""
    var age: Int = 0

    fun name(value: String) {
        name = value
    }

    fun age(value: Int) {
        age = value
    }

    fun build(): Person {
        return Person(name, age)
    }
}

fun buildPerson(block: PersonBuilder.() -> Unit): Person {
    val personBuilder = PersonBuilder()
    personBuilder.block()
    return personBuilder.build()
}
