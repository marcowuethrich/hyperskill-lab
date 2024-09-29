package sorting

import java.io.File
import java.io.FileNotFoundException
import java.util.*

fun main(args: Array<String>) {
    val sortingType: String
    val dataType: String
    val inputFile: String?
    val outputFile: String?
    val unknownArgs = mutableListOf<String>()

    if (args.contains("-sortingType")) {
        val index = args.indexOf("-sortingType") + 1
        if (index < args.size && !args[index].startsWith("-")) {
            sortingType = args[index]
        } else {
            println("No sorting type defined!")
            return
        }
    } else {
        sortingType = "natural"
    }

    if (args.contains("-dataType")) {
        val index = args.indexOf("-dataType") + 1
        if (index < args.size && !args[index].startsWith("-")) {
            dataType = args[index]
        } else {
            println("No data type defined!")
            return
        }
    } else {
        dataType = "word"
    }

    inputFile = if (args.contains("-inputFile")) {
        val index = args.indexOf("-inputFile") + 1
        if (index < args.size && !args[index].startsWith("-")) {
            args[index]
        } else {
            println("No input file defined!")
            return
        }
    } else null

    outputFile = if (args.contains("-outputFile")) {
        val index = args.indexOf("-outputFile") + 1
        if (index < args.size && !args[index].startsWith("-")) {
            args[index]
        } else {
            println("No output file defined!")
            return
        }
    } else null

    args.filter { it.startsWith("-") && it !in listOf("-sortingType", "-dataType", "-inputFile", "-outputFile") }
        .forEach {
            unknownArgs.add(it)
            println("\"$it\" is not a valid parameter. It will be skipped.")
        }

    val input = if (inputFile != null) {
        try {
            File(inputFile).readLines()
        } catch (e: FileNotFoundException) {
            println("Input file not found!")
            return
        }
    } else {
        Scanner(System.`in`).use { scanner ->
            generateSequence { if (scanner.hasNext()) scanner.nextLine() else null }
                .toList()
        }
    }

    val output = StringBuilder()

    when (dataType) {
        "long" -> {
            val longInput = input.flatMap { it.split("\\s+".toRegex()) }
                .filter { it.toLongOrNull() != null }
            val numbers = longInput.map { it.toLong() }
            val totalNumbers = numbers.size

            if (sortingType == "natural") {
                output.appendLine("Total numbers: $totalNumbers.")
                output.appendLine("Sorted data: ${numbers.sorted().joinToString(" ")}")
            } else {
                val frequencyMap = numbers.groupingBy { it }.eachCount()
                val sortedByCount = frequencyMap.entries.sortedWith(compareBy({ it.value }, { it.key }))
                output.appendLine("Total numbers: $totalNumbers.")
                sortedByCount.forEach { (num, count) ->
                    val percentage = (count.toDouble() / totalNumbers * 100).toInt()
                    output.appendLine("$num: $count time(s), $percentage%")
                }
            }
        }

        "line" -> {
            val totalLines = input.size
            if (sortingType == "natural") {
                output.appendLine("Total lines: $totalLines")
                output.appendLine("Sorted data:")
                input.sorted().forEach { output.appendLine(it) }
            } else {
                val frequencyMap = input.groupingBy { it }.eachCount()
                val sortedByCount = frequencyMap.entries.sortedWith(compareBy({ it.value }, { it.key }))
                output.appendLine("Total lines: $totalLines")
                sortedByCount.forEach { (line, count) ->
                    val percentage = (count.toDouble() / totalLines * 100).toInt()
                    output.appendLine("$line: $count time(s), $percentage%")
                }
            }
        }

        else -> { // "word" by default
            val totalWords = input.flatMap { it.split("\\s+".toRegex()) }.size

            if (sortingType == "natural") {
                output.appendLine("Total words: $totalWords.")
                output.appendLine(
                    "Sorted data: ${
                        input.flatMap { it.split("\\s+".toRegex()) }.sorted().joinToString(" ")
                    }"
                )
            } else {
                val wordsList = input.flatMap { it.split("\\s+".toRegex()) }
                val frequencyMap = wordsList.groupingBy { it }.eachCount()
                val sortedByCount = frequencyMap.entries.sortedWith(compareBy({ it.value }, { it.key }))
                output.appendLine("Total words: $totalWords.")
                sortedByCount.forEach { (word, count) ->
                    val percentage = (count.toDouble() / totalWords * 100).toInt()
                    output.appendLine("$word: $count time(s), $percentage%")
                }
            }
        }
    }

    if (outputFile != null) {
        File(outputFile).writeText(output.toString())
    } else {
        print(output)
    }
}
