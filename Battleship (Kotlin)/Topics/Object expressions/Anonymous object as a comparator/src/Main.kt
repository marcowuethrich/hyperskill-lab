class Sorted {
    data class Person(val name: String, val age: Int)

    val people = listOf(
        Person("Alice", 25),
        Person("Bob", 20),
        Person("Charlie", 30)
    )

    val sortedByAge = people.sortedWith(object : Comparator<Person> {
        override fun compare(o1: Person, o2: Person) = o1.age.compareTo(o2.age)
    })
}
