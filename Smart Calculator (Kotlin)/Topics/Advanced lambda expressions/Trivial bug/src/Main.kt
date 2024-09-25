class Solution {
    private val peaceWords = arrayOf("I", "&&n", 3, "love", 1, "peace", "&&n")
    private val pattern = Regex("\\d+")
    val filteredPeaceWords = peaceWords.filter {
        !(pattern.matches(it.toString()) || it.toString() == "&&n")
    }
}
