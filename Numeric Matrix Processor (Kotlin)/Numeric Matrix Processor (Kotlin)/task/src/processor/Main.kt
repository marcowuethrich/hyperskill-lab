package processor

fun main() {
    val reader = System.`in`.bufferedReader()

    fun readMatrix(n: Int, m: Int): List<List<Double>> {
        return List(n) { reader.readLine().trim().split(" ").mapNotNull { it.toDoubleOrNull() } }
    }

    fun transposeMatrix(matrix: List<List<Double>>, type: Int): List<List<Double>> {
        val n = matrix.size
        val m = matrix[0].size
        return when (type) {
            1 -> List(m) { i -> List(n) { j -> matrix[j][i] } } // Main diagonal
            2 -> List(m) { i -> List(n) { j -> matrix[n - j - 1][m - i - 1] } } // Side diagonal
            3 -> List(n) { i -> List(m) { j -> matrix[i][m - j - 1] } } // Vertical line
            4 -> List(n) { i -> List(m) { j -> matrix[n - i - 1][j] } } // Horizontal line
            else -> matrix // Default case, no transposition
        }
    }

    fun determinant(matrix: List<List<Double>>, memo: MutableMap<List<List<Double>>, Double> = mutableMapOf()): Double {
        memo[matrix]?.let { return it }

        if (matrix.size == 1) return matrix[0][0]
        if (matrix.size == 2) return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]

        var det = 0.0
        for (j in matrix[0].indices) {
            val minor = matrix.drop(1).map { row -> row.filterIndexed { index, _ -> index != j } }
            det += matrix[0][j] * determinant(minor, memo) * if (j % 2 == 0) 1 else -1
        }

        memo[matrix] = det
        return det
    }

    fun cofactorMatrix(matrix: List<List<Double>>): List<List<Double>> {
        val n = matrix.size
        return List(n) { i ->
            List(n) { j ->
                val minor = matrix.filterIndexed { rowIndex, _ -> rowIndex != i }
                    .map { row -> row.filterIndexed { colIndex, _ -> colIndex != j } }
                (if ((i + j) % 2 == 0) 1 else -1) * determinant(minor)
            }
        }
    }

    fun inverseMatrix(matrix: List<List<Double>>): List<List<Double>> {
        val det = determinant(matrix)
        if (det == 0.0) {
            throw IllegalArgumentException("This matrix doesn't have an inverse.")
        }
        val cofactor = cofactorMatrix(matrix)
        val adjugate = transposeMatrix(cofactor, 1)
        return adjugate.map { row -> row.map { element -> element / det } }
    }

    while (true) {
        println("1. Add matrices")
        println("2. Multiply matrix by a constant")
        println("3. Multiply matrices")
        println("4. Transpose matrix")
        println("5. Calculate a determinant")
        println("6. Inverse matrix")
        println("0. Exit")
        print("Your choice: ")

        when (reader.readLine().toIntOrNull()) {
            1 -> {
                println("Enter size of first matrix: > ")
                val dimensionsA = reader.readLine().trim().split(" ").mapNotNull { it.toIntOrNull() }
                val matrixA = readMatrix(dimensionsA[0], dimensionsA[1])
                println("Enter size of second matrix: > ")
                val dimensionsB = reader.readLine().trim().split(" ").mapNotNull { it.toIntOrNull() }
                val matrixB = readMatrix(dimensionsB[0], dimensionsB[1])

                if (matrixA.size != matrixB.size || matrixA[0].size != matrixB[0].size) {
                    println("The operation cannot be performed.")
                } else {
                    val result = matrixA.zip(matrixB).map { (rowA, rowB) ->
                        rowA.zip(rowB) { a, b -> a + b }
                    }
                    println("The result is:")
                    result.forEach { row ->
                        println(row.joinToString(" ") { it.toString() })
                    }
                }
            }

            2 -> {
                println("Enter size of matrix: ")
                val dimensions = reader.readLine().trim().split(" ").mapNotNull { it.toIntOrNull() }
                val matrixA = readMatrix(dimensions[0], dimensions[1])
                println("Enter constant: ")
                val constant = reader.readLine().toDouble()

                val result = matrixA.map { row ->
                    row.map { element ->
                        element * constant
                    }
                }

                println("The result is:")
                result.forEach { row ->
                    println(row.joinToString(" ") { it.toString() })
                }
            }

            3 -> {
                println("Enter size of first matrix: ")
                val dimensionsA = reader.readLine().trim().split(" ").mapNotNull { it.toIntOrNull() }
                val matrixA = readMatrix(dimensionsA[0], dimensionsA[1])
                println("Enter size of second matrix: ")
                val dimensionsB = reader.readLine().trim().split(" ").mapNotNull { it.toIntOrNull() }
                val matrixB = readMatrix(dimensionsB[0], dimensionsB[1])

                if (matrixA[0].size != matrixB.size) {
                    println("The operation cannot be performed.")
                } else {
                    val result = List(matrixA.size) { i ->
                        List(matrixB[0].size) { j ->
                            var sum = 0.0
                            for (k in matrixA[0].indices) {
                                sum += matrixA[i][k] * matrixB[k][j]
                            }
                            sum
                        }
                    }

                    println("The result is:")
                    result.forEach { row ->
                        println(row.joinToString(" ") { it.toString() })
                    }
                }
            }

            4 -> {
                println("1. Main diagonal")
                println("2. Side diagonal")
                println("3. Vertical line")
                println("4. Horizontal line")
                print("Your choice: ")
                val transposeType = readln().toInt()
                print("Enter matrix size: ")
                val dimensions = reader.readLine().trim().split(" ").mapNotNull { it.toIntOrNull() }
                val matrix = readMatrix(dimensions[0], dimensions[1])
                val result = transposeMatrix(matrix, transposeType)
                println("The result is: ")
                result.forEach { row ->
                    println(row.joinToString(" ") { it.toString() })
                }
            }

            5 -> {
                print("Enter matrix size: ")
                val dimensions = reader.readLine().trim().split(" ").mapNotNull { it.toIntOrNull() }
                require(dimensions.size == 2 && dimensions[0] == dimensions[1]) {
                    "The operation cannot be performed for non-square matrices."
                }
                val matrix = readMatrix(dimensions[0], dimensions[1])
                println("The result is: ")
                println(determinant(matrix))
            }

            6 -> {
                print("Enter matrix size: ")
                val dimensions = reader.readLine().trim().split(" ").mapNotNull { it.toIntOrNull() }
                require(dimensions.size == 2 && dimensions[0] == dimensions[1]) {
                    "The operation cannot be performed for non-square matrices."
                }
                val matrix = readMatrix(dimensions[0], dimensions[1])
                try {
                    val inverse = inverseMatrix(matrix)
                    println("The result is:")
                    inverse.forEach { row ->
                        println(row.joinToString(" ") { String.format("%.2f", it) })
                    }
                } catch (e: IllegalArgumentException) {
                    println(e.message)
                }
            }

            0 -> {
                return
            }

            else -> {
                println("Invalid choice. Please try again.")
            }
        }
    }
}
