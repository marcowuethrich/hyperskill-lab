package tictactoe

fun main() {
    val grid = listOf(
        mutableListOf(' ', ' ', ' '),
        mutableListOf(' ', ' ', ' '),
        mutableListOf(' ', ' ', ' '),
    )
    printGrid(grid)

    var gameIsRunning = true
    while (gameIsRunning) {
        readln().toCharArray().filter { !Character.isWhitespace(it) }.let {
            if (!it.containsOnlyDigits()) {
                println("You should enter numbers!")
                return@let
            }
            if (!it.inRange()) {
                println("Coordinates should be from 1 to 3!")
                return@let
            }
            val x = it[1].digitToInt()
            val y = it[0].digitToInt()
            if (listOf('X', 'O').any { it == grid[y - 1][x - 1] }) {
                println("This cell is occupied! Choose another one!")
                return@let
            }

            grid[y - 1][x - 1] = getUserChar(grid)
            printGrid(grid)

            val lines: List<List<Char>> = extractAllLines(grid)
            when {
                checkUserWin(lines, 'X') -> gameIsRunning = stopGameWithMessage("X wins")
                checkUserWin(lines, 'O') -> gameIsRunning = stopGameWithMessage("O wins")
                checkDraw(lines) -> gameIsRunning = stopGameWithMessage("Draw")
            }
        }
    }

}

fun stopGameWithMessage(message: String) = false.also { println(message) }

fun List<Char>.containsOnlyDigits(): Boolean = this.all { it.isDigit() }
fun List<Char>.inRange(range: CharRange = '1'..'3'): Boolean = this.all { it in range }

fun getUserChar(grid: List<MutableList<Char>>): Char = grid
    .flatten()
    .let { it.filter { it == 'X' }.size <= it.filter { it == 'O' }.size }
    .let { if (it) 'X' else 'O' }

fun extractAllLines(grid: List<List<Char>>): List<List<Char>> = listOf(
    // horizontal
    grid[0],
    grid[1],
    grid[2],
    // vertical
    listOf(grid[0][0], grid[1][0], grid[2][0]),
    listOf(grid[0][1], grid[1][1], grid[2][1]),
    listOf(grid[0][2], grid[1][2], grid[2][2]),
    // diagonal
    listOf(grid[0][0], grid[1][1], grid[2][2]),
    listOf(grid[0][2], grid[1][1], grid[2][0]),
)

fun checkUserWin(lines: List<List<Char>>, player: Char): Boolean = lines.any { it.all { it == player } }
fun checkDraw(lines: List<List<Char>>): Boolean = lines.none { it.any { it.isWhitespace() } }

fun printGrid(grid: List<List<Char>>) {
    println("---------")
    for (row in grid) {
        println("| ${row[0]} ${row[1]} ${row[2]} |")
    }
    println("---------")
}
