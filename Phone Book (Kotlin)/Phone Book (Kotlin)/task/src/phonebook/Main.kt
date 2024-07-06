package phonebook

import java.io.File
import kotlin.math.sqrt
import kotlin.system.measureTimeMillis

fun main() {
    val baseDir = "/Users/marco/Downloads/data"
    val directoryLines = File("$baseDir/directory.txt").readLines()
    val sortedDirectoryFile = File("$baseDir/sorted_directory.txt")
    val people = File("$baseDir/find.txt").readLines()

    val linearSearchDuration = execLinearSearch(people, directoryLines)

    println()
    execBubbleSortAndJumpSearch(people, directoryLines, sortedDirectoryFile, linearSearchDuration)

    println()
    execQuickSortAndBinarySearch(people, sortedDirectoryFile, directoryLines)

    println()
    execHashTableSearch(people, directoryLines)
}

fun execLinearSearch(people: List<String>, directoryLines: List<String>): Long {
    println("Start searching (linear search)...")
    val foundPeoples = mutableListOf<String>()
    val linSearchDuration = measureTimeMillis {
        linearSearch(people, directoryLines, foundPeoples)
    }
    println("Found ${foundPeoples.size} / ${people.size} entries. Time taken: ${linSearchDuration.printDuration()}")
    return linSearchDuration
}

private fun linearSearch(
    people: List<String>,
    directoryLines: List<String>,
    foundPeoples: MutableList<String>
) =
    people.forEach { target ->
        directoryLines.forEach { line ->
            if (line.contains(target) && !foundPeoples.contains(target)) {
                foundPeoples.add(target)
            }
        }
    }

fun execBubbleSortAndJumpSearch(
    people: List<String>,
    directoryLines: List<String>,
    sortedDirectoryFile: File,
    linearSearchDuration: Long
) {
    println("Start searching (bubble sort + jump search)...")
    val bubbleSortedDirLines =
        if (sortedDirectoryFile.exists()) sortedDirectoryFile.readLines().toMutableList()
        else directoryLines.toMutableList()

    val startDuration = System.currentTimeMillis()
    var isBubbleSorted = false
    if (!sortedDirectoryFile.exists()) {
        isBubbleSorted = bubbleSort(bubbleSortedDirLines, startDuration, linearSearchDuration)
    }
    val bubbleSortDuration = System.currentTimeMillis() - startDuration

//    if (!sortedDirectoryFile.exists()) sortedDirectoryFile.writeText(bubbleSortedDirLines.joinToString("\n"))

    val foundPeoples: MutableList<String> = mutableListOf()
    val jumpSearchDuration = measureTimeMillis {
        if (!isBubbleSorted) {
            // linearSearch
            linearSearch(people, directoryLines, foundPeoples)
        } else {
            // jumpSearch
            people.forEach { person ->
                val jumpSize = sqrt(bubbleSortedDirLines.size.toDouble()).toInt()
                var lastLeft = -1
                var lastRight = -1
                var left = 0
                var right: Int = jumpSize
                while (lastLeft != left && lastRight != right) {
                    lastLeft = left
                    lastRight = right
                    if (right < bubbleSortedDirLines.size && bubbleSortedDirLines[right].substringAfter(" ").trim()
                            .compareTo(person, ignoreCase = true) < 0
                    ) {
                        left = right
                        right += jumpSize
                        if (right >= bubbleSortedDirLines.size) {
                            right = bubbleSortedDirLines.size - 1
                        }
                    }
                }

                if (bubbleSortedDirLines.subList(left, right + 1).any { it.substringAfter(" ").trim() == person }) {
                    foundPeoples.add(person)
                }
            }
        }
    }

    println("Found ${foundPeoples.size} / ${people.size} entries. Time taken: ${(bubbleSortDuration + jumpSearchDuration).printDuration()}")
    println("Sorting time: ${bubbleSortDuration.printDuration()}")
    println("Searching time: ${jumpSearchDuration.printDuration()}")
}


fun execQuickSortAndBinarySearch(people: List<String>, sortedDirectoryFile: File, directoryLines: List<String>) {
    println("Start searching (quick sort + binary search)...")

    var quickSortedDirLines: List<String> = listOf()
    var quickSortDuration = 0L

    if (!sortedDirectoryFile.exists()) {
        quickSortDuration = measureTimeMillis {
            quickSortedDirLines = quicksort(
                if (sortedDirectoryFile.exists()) sortedDirectoryFile.readLines()
                    .toMutableList() else directoryLines.toMutableList()
            )
        }
    }
//    if (!sortedDirectoryFile.exists()) sortedDirectoryFile.writeText(quickSortedDirLines.joinToString("\n"))

    val foundPeoples: MutableList<String> = mutableListOf()
    val binarySearchDuration = measureTimeMillis {
        people.forEach { target ->
            val foundIndex = binarySearch(quickSortedDirLines, target, 0, quickSortedDirLines.size - 1)
            if (foundIndex >= 0) foundPeoples.add(target)
        }
    }

    println("Found ${foundPeoples.size} / ${people.size} entries. Time taken: ${(quickSortDuration + binarySearchDuration).printDuration()}")
    println("Sorting time: ${quickSortDuration.printDuration()}")
    println("Searching time: ${binarySearchDuration.printDuration()}")

}

fun execHashTableSearch(people: List<String>, directoryLines: List<String>) {
    println("Start searching (hash table)...")
    val hashTable = HashMap<String, String>(directoryLines.size)
    val createDuration = measureTimeMillis {
        directoryLines.forEach { line ->
            hashTable[line.substringAfter(" ")] = line
        }
    }

    val foundPeoples: MutableList<String> = mutableListOf()
    val hashTableSearchDuration = measureTimeMillis {
        people.forEach { target -> hashTable[target]?.let { foundPeoples.add(it) } }
    }

    println("Found ${foundPeoples.size} / ${people.size} entries. Time taken: ${(createDuration + hashTableSearchDuration).printDuration()}")
    println("Creating time: ${createDuration.printDuration()}")
    println("Searching time: ${hashTableSearchDuration.printDuration()}")
}

fun Long.printDuration(): String = String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", this)

fun bubbleSort(directory: MutableList<String>, startMilliseconds: Long, linearSearchDuration: Long): Boolean {
    var swapped: Boolean

    for (pass in directory.indices) {
        swapped = false

        val tempDuration = System.currentTimeMillis() - startMilliseconds
        if (tempDuration > (linearSearchDuration * 10)) return false

        for (currentPosition in 0 until (directory.size - pass - 1)) {
            val currentElementNamePart = directory[currentPosition].substringAfter(" ").trim()
            val nextElementNamePart = directory[currentPosition + 1].substringAfter(" ").trim()

            if (currentElementNamePart.compareTo(nextElementNamePart, ignoreCase = true) > 0) {
                directory[currentPosition] =
                    directory[currentPosition + 1].also { directory[currentPosition + 1] = directory[currentPosition] }
                swapped = true
            }
        }

        if (!swapped) break
    }
    return true
}

fun quicksort(list: List<String>): List<String> {
    if (list.size < 2) return list

    val pivot = list[list.size / 2].substringAfter(" ")
    val less = list.filter { it.substringAfter(" ") < pivot }
    val equal = list.filter { it.substringAfter(" ") == pivot }
    val greater = list.filter { it.substringAfter(" ") > pivot }

    return quicksort(less) + equal + quicksort(greater)
}

fun binarySearch(list: List<String>, target: String, start: Int, end: Int): Int {
    if (start > end) return -1 // base

    val mid = (start + end) / 2
    return when {
        list[mid].substringAfter(" ").contains(target) -> mid
        list[mid].substringAfter(" ") > target -> binarySearch(list, target, start, mid - 1)
        else -> binarySearch(list, target, mid + 1, end)
    }
}
