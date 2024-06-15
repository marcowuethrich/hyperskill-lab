fun main() {
    val numberOfCompanies = readln().toInt()
    val companyIncomes = IntArray(numberOfCompanies).map { readln().toInt() }
    val companyTaxes = IntArray(numberOfCompanies).map { readln().toInt() }

    val companyTotalTaxes = companyIncomes.mapIndexed { index, income -> income * companyTaxes[index] }

    var largestTaxes = -1
    var largestTaxCompanyIndex = -1
    companyTotalTaxes.forEachIndexed { index, total ->
        if (largestTaxes < total) {
            largestTaxes = total
            largestTaxCompanyIndex = index
        }
    }
    println(largestTaxCompanyIndex + 1)
}
