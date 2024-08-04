package mazerunner

import java.io.File
import kotlin.random.Random.Default.nextInt

fun main() = MazeRunner.run()

class MazeRunner private constructor() {
    enum class MenuItem(val key: Int, val label: String) {
        EXIT(0, "Exit"),
        GENERATE(1, "Generate a new maze"),
        LOAD(2, "Load a maze"),
        SAVE(3, "Save the maze"),
        DISPLAY(4, "Display the maze"),
        FIND_ESCAPE(5, "Find the escape");

        override fun toString() = "$key. $label"
    }

    private val WALL = 1
    private val PASSAGE = 0
    private val PATH = 2
    private lateinit var maze: Array<Array<Int>>

    companion object {
        fun run() {
            val runner = MazeRunner()
            runner.start()
        }
    }

    private fun start() {
        var running = true
        while (running) {
            printMenu()
            when (readln().toIntOrNull()) {
                MenuItem.GENERATE.key -> generateMaze().also { print(maze) }
                MenuItem.LOAD.key -> loadMaze()
                MenuItem.SAVE.key -> {
                    if (::maze.isInitialized) save() else println("Incorrect option. Please try again")
                }

                MenuItem.DISPLAY.key -> {
                    if (::maze.isInitialized) print(maze) else println("Incorrect option. Please try again")
                }

                MenuItem.FIND_ESCAPE.key -> {
                    if (::maze.isInitialized) findTheEscape() else println("Incorrect option. Please try again")
                }

                MenuItem.EXIT.key -> {
                    running = false
                    println("Bye!")
                }

                else -> println("Incorrect option. Please try again")
            }
        }
    }

    private fun printMenu() {
        val builder = StringBuilder()
        builder.appendLine("=== Menu ===")
        builder.appendLine(MenuItem.GENERATE)
        builder.appendLine(MenuItem.LOAD)
        if (::maze.isInitialized) {
            builder.appendLine(MenuItem.SAVE)
            builder.appendLine(MenuItem.DISPLAY)
            builder.appendLine(MenuItem.FIND_ESCAPE)
        }
        builder.appendLine(MenuItem.EXIT)
        print(builder.toString())
    }

    private fun save() = File(readln())
        .writeText(maze.joinToString("\n")
        { row -> row.joinToString(",") })

    private fun loadMaze() = readln().let {
        val file = File(it)
        if (file.exists()) {
            try {
                maze = file.readLines().map { line ->
                    line.split(",").map { cell -> cell.toInt() }.toIntArray().toTypedArray()
                }.toTypedArray()
            } catch (e: Exception) {
                println("Cannot load the maze. It has an invalid format")
            }
        } else {
            println("The file $it does not exist")
        }
    }


    private fun generateMaze() {
        println("Enter the size of a new maze")
        val size = readln().toInt()
        maze = Array(size) { Array(size) { WALL } }

        fun markAsPassage(index: Pair<Int, Int>) {
            maze[index.first][index.second] = PASSAGE
        }

        fun findFrontierCells(index: Pair<Int, Int>, onlyCellOfType: Int = WALL): List<Pair<Int, Int>> {
            val frontierCells: MutableList<Pair<Int, Int>> = mutableListOf()
            val directions = listOf(Pair(-2, 0), Pair(2, 0), Pair(0, -2), Pair(0, 2))
            directions.forEach { direction ->
                val frontierCell = Pair(index.first + direction.first, index.second + direction.second)

                if (frontierCell.first < 1 || frontierCell.first > maze.size - 1) return@forEach
                if (frontierCell.second < 1 || frontierCell.second > maze.first().size - 1) return@forEach

                val isWall = maze[frontierCell.first][frontierCell.second] == onlyCellOfType
                if (isWall) frontierCells.add(frontierCell)

            }
            return frontierCells
        }

        fun findCellBetween(point: Pair<Int, Int>): Pair<Int, Int> {
            val point2 = findFrontierCells(point, PASSAGE).first()
            return Pair((point.first + point2.first) / 2, (point.second + point2.second) / 2)
        }

        // Pick a random cell, set it as Passage and compute its frontier cells.
        // Frontier cell is a cell with distance 2 in state of Blocked and within the grid
        var selectedCell = Pair(maze.size - 2, maze[0].size - 2)
        markAsPassage(selectedCell)
        val frontiers = mutableSetOf<Pair<Int, Int>>().apply { addAll(findFrontierCells(selectedCell)) }

        // While the list of frontier cells is not empty:
        // pick a random frontier
        // set the sell between to passage
        // compute the frontier cells of the chosen one and add them to the frontier list
        // Remove the chosen frontier cell from the list of frontier cells (to passage)
        while (frontiers.isNotEmpty()) {
            val nextSelectedCell = frontiers.elementAt(nextInt(0, frontiers.size))
            val cellBetween = findCellBetween(nextSelectedCell)
            markAsPassage(nextSelectedCell)
            markAsPassage(cellBetween)
            selectedCell = nextSelectedCell
            frontiers.addAll(findFrontierCells(selectedCell))
            frontiers.remove(nextSelectedCell)
        }

        fun addEntrances() {
            val possibleEntries1 = mutableListOf<Pair<Int, Int>>()
            val possibleEntries2 = mutableListOf<Pair<Int, Int>>()
            maze.forEachIndexed { index, row ->
                if (row[1] == PASSAGE) possibleEntries1.add(index to 0)
                if (row[row.lastIndex - 1] == PASSAGE) possibleEntries2.add(index to row.lastIndex)
            }
            markAsPassage(possibleEntries1.elementAt(nextInt(0, possibleEntries1.size)))
            markAsPassage(possibleEntries2.elementAt(nextInt(0, possibleEntries2.size)))
        }

        addEntrances()
    }

    private fun findTheEscape() {
        val resolvedMaze = maze.copyOf()

        val entrypoints = resolvedMaze
            .mapIndexedNotNull { index, row ->
                when (PASSAGE) {
                    row[0] -> index to 0
                    row[row.lastIndex] -> index to row.lastIndex
                    else -> null
                }
            }

        val entrypoint1 = entrypoints.first()
        val entrypoint2 = entrypoints.last()

        // find the escape in the maze using the DFS algorithmus
        val visited = mutableSetOf<Pair<Int, Int>>()
        val stack = mutableListOf(entrypoint1)
        val cameFrom = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
        val dirs = listOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1))

        while (stack.isNotEmpty()) {
            val v = stack.removeAt(stack.lastIndex)

            if (v in visited) continue
            visited.add(v)

            if (v == entrypoint2) {
                var current = v
                while (current in cameFrom) {
                    val prev = cameFrom[current]!!
                    resolvedMaze[prev.first][prev.second] = PATH
                    current = prev
                }
                resolvedMaze[entrypoint2.first][entrypoint2.second] = PATH
                break
            }

            for (dir in dirs) {
                val next = Pair(v.first + dir.first, v.second + dir.second)
                if (next.first !in 0 until resolvedMaze.size) continue
                if (next.second !in 0 until resolvedMaze[next.first].size) continue
                if (resolvedMaze[next.first][next.second] == WALL) continue
                if (next in visited) continue

                cameFrom[next] = v
                stack.add(next)
            }
        }

        print(resolvedMaze)
    }

    private fun print(maze: Array<Array<Int>>) = maze.forEach { rows ->
        rows.forEach { cell ->
            when (cell) {
                PASSAGE -> print("  ")
                PATH -> print("//")
                WALL -> print("\u2588\u2588")
            }
        }.also { println() }
    }
}
