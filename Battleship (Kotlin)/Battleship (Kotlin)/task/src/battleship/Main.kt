package battleship

val COLUMN_HEADER = (1..10).toList()
val ROW_HEADER = ('A'..'J').toList()

fun main() {
    val battleship = Battleship()
    battleship.addShips()
    battleship.startGame()
}

class Battleship {
    private val board1 = generateNewField()
    private val board2 = generateNewField()

    fun addShips() {
        // player 1
        println("Player 1, place your ships on the game field")
        printBoard(board1)
        Ship.values().forEach { letUserAddNewShip(board1, it) }

        println("Press Enter and pass the move to another player").also { readln() }
        // player 2
        println("Player 2, place your ships to the game field")
        printBoard(board2)
        Ship.values().forEach { letUserAddNewShip(board2, it) }
        println("Press Enter and pass the move to another player").also { readln() }
    }

    private fun printBoard(board: ArrayList<Array<Char>>, hideShips: Boolean = false) {
        println("  " + COLUMN_HEADER.joinToString(" "))
        ROW_HEADER
            .forEachIndexed { index, rowHeader ->
                println("$rowHeader ${
                    board[index]
                        .map { if (hideShips && it == Flags.SHIP.symbol) Flags.FOG.symbol else it }
                        .joinToString(" ")
                }")
            }
            .also { println() }
    }

    private fun printBoards(playerBoard: ArrayList<Array<Char>>, opponentBoard: ArrayList<Array<Char>>) {
        printBoard(opponentBoard, true)
        println("---------------------")
        printBoard(playerBoard)
    }

    private fun letUserAddNewShip(board: ArrayList<Array<Char>>, ship: Ship) {
        println("Enter the coordinates of the ${ship.label} (${ship.cells} cells):")
        do {
            val userInput = readln().trim()
            val valid = validateShipCoordinates(board, userInput, ship)
            if (!valid) continue
            addShip(board, userInput)
            printBoard(board)
            break
        } while (true)
    }

    private fun generateNewField() = ArrayList<Array<Char>>().apply {
        repeat(10) { add(Array(10) { Flags.FOG.symbol }) }
    }

    private fun addShip(board: ArrayList<Array<Char>>, userInput: String) {
        val cell1 = userInput.split(" ")[0]
        val cell2 = userInput.split(" ")[1]
        val cell1Letter = cell1[0]
        val cell1Digit = cell1.substringAfter(cell1Letter).toInt()
        val cell2Letter = cell2[0]
        val cell2Digit = cell2.substringAfter(cell2Letter).toInt()
        val shipIndices = coordinatesToIndices(cell1Digit, cell1Letter, cell2Digit, cell2Letter)

        shipIndices.forEach { flagFieldCell(board, it, Flags.SHIP.symbol) }
    }

    private fun flagFieldCell(board: ArrayList<Array<Char>>, indices: Pair<Int, Int>, flag: Char) {
        board[indices.second][indices.first] = flag
    }

    private fun validateShipCoordinates(board: ArrayList<Array<Char>>, userInput: String, ship: Ship): Boolean {
        if (!userInput.contains(" ")) {
            println("Error! Invalid input coordinates. Try again:")
            return false
        }
        val cell1 = userInput.split(" ")[0]
        val cell2 = userInput.split(" ")[1]
        if (cell1 == cell2) {
            println("Error! Invalid coordinates. They are the same! Try again:")
            return false
        }
        val cell1Letter = cell1[0]
        val cell1Digit = cell1.substringAfter(cell1Letter).toIntOrNull()
        val cell2Letter = cell2[0]
        val cell2Digit = cell2.substringAfter(cell2Letter).toIntOrNull()

        if (cell1Digit == null || cell2Digit == null) {
            println("Error! Invalid coordinates. Second char of coordinate must be a digit. Try again:")
            return false
        }

        if (cell1Digit !in COLUMN_HEADER || cell2Digit !in COLUMN_HEADER) {
            println("Error! Invalid coordinates. Column must be a digit from 1 to 10! Try again:")
            return false
        }
        if (cell1Letter !in ROW_HEADER || cell2Letter !in ROW_HEADER) {
            println("Error! Invalid coordinates. Row must be a letter from A to J! Try again:")
            return false
        }
        if (cell1Letter != cell2Letter && cell1Digit != cell2Digit) {
            println("Error! Only horizontal and vertical coordinates are supported! Try again:")
            return false
        }

        if (cell2Letter == cell1Letter) {
            val dif = if (cell1Digit < cell2Digit) cell2Digit - cell1Digit else cell1Digit - cell2Digit
            if ((dif + 1) != ship.cells) {
                println("Error! Invalid ship size. It should be ${ship.cells} cells long! Try again:")
                return false
            }
        }

        if (cell2Digit == cell1Digit) {
            val dif = if (cell1Letter < cell2Letter) cell2Letter - cell1Letter else cell1Letter - cell2Letter
            if ((dif + 1) != ship.cells) {
                println("Error! Invalid ship size. It should be ${ship.cells} cells long! Try again:")
                return false
            }
        }

        val shipIndices = coordinatesToIndices(cell1Digit, cell1Letter, cell2Digit, cell2Letter)

        if (areCellsOccupied(board, shipIndices)) {
            println("Error! Already occupied. Try again:")
            return false
        }

        if (areNeighborCellsOccupied(board, shipIndices)) {
            println("Error! You placed it too close to another one. Try again:")
            return false
        }

        return true
    }

    private fun coordinatesToIndices(x1: Int, y1: Char, x2: Int, y2: Char): List<Pair<Int, Int>> {
        var startXY = x1 - 1 to ROW_HEADER.indexOf(y1)
        var endXY = x2 - 1 to ROW_HEADER.indexOf(y2)

        // swap coordinates if upside down
        if (startXY.first > endXY.first) {
            val temp = startXY.first
            startXY = endXY.first to startXY.second
            endXY = temp to endXY.second
        }
        if (startXY.second > endXY.second) {
            val temp = startXY.second
            startXY = startXY.first to endXY.second
            endXY = endXY.first to temp
        }

        val indices: MutableList<Pair<Int, Int>> = mutableListOf()
        for (i in startXY.first..endXY.first) {
            for (j in startXY.second..endXY.second) {
                indices.add(i to j)
            }
        }
        return indices
    }

    private fun userInputToIndices(userInput: String): Pair<Int, Int> {
        val y = userInput[0]
        val x = userInput.substringAfter(y).toInt()
        return x - 1 to ROW_HEADER.indexOf(y)
    }

    private fun areCellsOccupied(board: ArrayList<Array<Char>>, indices: List<Pair<Int, Int>>): Boolean =
        indices.any { board[it.second][it.first] != Flags.FOG.symbol }

    private fun areNeighborCellsOccupied(board: ArrayList<Array<Char>>, indices: List<Pair<Int, Int>>): Boolean =
        indices.any {
            try {
                if (board[it.second + 1][it.first] != Flags.FOG.symbol) return@any true
            } catch (ignored: IndexOutOfBoundsException) {
            }
            try {
                if (board[it.second - 1][it.first] != Flags.FOG.symbol) return@any true
            } catch (ignored: IndexOutOfBoundsException) {
            }
            try {
                if (board[it.second][it.first + 1] != Flags.FOG.symbol) return@any true
            } catch (ignored: IndexOutOfBoundsException) {
            }
            try {
                if (board[it.second][it.first - 1] != Flags.FOG.symbol) return@any true
            } catch (ignored: IndexOutOfBoundsException) {
            }
            return@any false
        }

    private fun containsNeighborCellAShip(board: ArrayList<Array<Char>>, indices: Pair<Int, Int>): Boolean {
        try {
            if (board[indices.second + 1][indices.first] == Flags.SHIP.symbol) return true
        } catch (ignored: IndexOutOfBoundsException) {
        }
        try {
            if (board[indices.second - 1][indices.first] == Flags.SHIP.symbol) return true
        } catch (ignored: IndexOutOfBoundsException) {
        }
        try {
            if (board[indices.second][indices.first + 1] == Flags.SHIP.symbol) return true
        } catch (ignored: IndexOutOfBoundsException) {
        }
        try {
            if (board[indices.second][indices.first - 1] == Flags.SHIP.symbol) return true
        } catch (ignored: IndexOutOfBoundsException) {
        }
        return false
    }

    private fun fieldContainsAnyShip(board: ArrayList<Array<Char>>) =
        board.any { row -> row.any { cell -> cell == Flags.SHIP.symbol } }


    fun startGame() {
        var isRunning = true
        var player1Turn = true
        var playerBoard = board1
        var opponentBoard = board2

        do {
            printBoards(playerBoard, opponentBoard)
            println("${if (player1Turn) "Player 1" else "Player 2"}, it's your turn:")
            var userInput: String
            do {
                userInput = readln().trim()
                if (userInput[0] !in ROW_HEADER || userInput.substringAfter(userInput[0]).toInt() !in COLUMN_HEADER) {
                    println("Error: Invalid input! Try again:")
                    continue
                }
                break
            } while (true)

            val indices = userInputToIndices(userInput)
            val cellValue = board1[indices.second][indices.first]

            when {
                cellValue == Flags.FOG.symbol -> {
                    flagFieldCell(opponentBoard, indices, Flags.MISS.symbol)
                    println("You missed.")
                }

                listOf(Flags.SHIP.symbol, Flags.HIT.symbol).contains(cellValue) -> {
                    flagFieldCell(opponentBoard, indices, Flags.HIT.symbol)
                    if (!fieldContainsAnyShip(opponentBoard)) {
                        println("You sank the last ship. You won. Congratulations!")
                        isRunning = false
                    } else {
                        if (containsNeighborCellAShip(opponentBoard, indices)) {
                            println("You hit a ship!")
                        } else {
                            println("You sank a ship!")
                        }
                    }
                }
            }
            if (isRunning) {
                player1Turn = !player1Turn
                // swap boards
                with(playerBoard) {
                    playerBoard = opponentBoard
                    opponentBoard = this
                }
                println("Press Enter and pass the move to another player").also { readln() }
            }
        } while (isRunning)
    }
}

enum class Ship(val cells: Int, val label: String) {
    AIRCRAFT_CARRIER(5, "Aircraft Carrier"),
    BATTLESHIP(4, "Battleship"),
    SUBMARINE(3, "Submarine"),
    CRUISER(3, "Cruiser"),
    DESTROYER(2, "Destroyer"),
}

enum class Flags(val symbol: Char) {
    FOG('~'),
    SHIP('O'),
    HIT('X'),
    MISS('M')
}
