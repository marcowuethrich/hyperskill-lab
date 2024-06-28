class Kitty(val color: String, val age: Int) {
    constructor() : this("", 0) {}
    constructor(color: String) : this(color, 0) {}
    constructor(age: Int, color: String) : this(color, age) {}
    constructor(age: Int) : this("", age) {}
}
