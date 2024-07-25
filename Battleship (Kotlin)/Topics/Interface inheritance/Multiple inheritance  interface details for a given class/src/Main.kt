// Do not change the code below.  

interface Listed {
    val index: String
}

data class Element(
    override val column: Int,
    override val row: Int,
    var value: Int
) : Tabulated

// Do not change the code above.  

interface Tabulated : Listed {
    val column: Int
    val row: Int
    override val index: String
        get() = "$column, $row"
}
