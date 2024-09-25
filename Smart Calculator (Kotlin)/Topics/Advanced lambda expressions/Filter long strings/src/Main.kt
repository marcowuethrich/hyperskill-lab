class Solution {
    fun filterLongStrings(strings: List<String>, length: Int): List<String> {
        return strings.filter { it.length >= length }
    }
}
