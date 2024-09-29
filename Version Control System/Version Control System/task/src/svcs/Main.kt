package svcs

import java.io.File
import java.security.MessageDigest

fun main(args: Array<String>) {
    val vcsDir = File("vcs")
    if (!vcsDir.exists()) {
        vcsDir.mkdir()
    }

    val configFile = File(vcsDir, "config.txt")
    val indexFile = File(vcsDir, "index.txt")
    val logFile = File(vcsDir, "log.txt")
    val commitsDir = File(vcsDir, "commits")
    if (!commitsDir.exists()) {
        commitsDir.mkdir()
    }

    val commands = mapOf(
        "config" to "Get and set a username.",
        "add" to "Add a file to the index.",
        "log" to "Show commit logs.",
        "commit" to "Save changes.",
        "checkout" to "Restore a file."
    )

    if (args.isEmpty() || args[0] == "--help") {
        println("These are SVCS commands:")
        commands.forEach { (command, description) ->
            println("$command     $description")
        }
        return
    }

    val command = args[0]
    val currentUsername = if (configFile.exists()) configFile.readText().trim() else "Unknown"

    when (command) {
        "config" -> {
            if (args.size == 1) {
                if (configFile.exists()) {
                    println("The username is ${configFile.readText().trim()}.")
                } else {
                    println("Please, tell me who you are.")
                }
            } else {
                configFile.writeText(args[1])
                println("The username is ${args[1]}.")
            }
        }

        "add" -> {
            if (args.size == 1) {
                if (indexFile.exists() && indexFile.readText().isNotEmpty()) {
                    println("Tracked files:")
                    println(indexFile.readText())
                } else {
                    println("Add a file to the index.")
                }
            } else {
                val fileName = args[1]
                val file = File(fileName)
                if (file.exists()) {
                    indexFile.appendText(fileName + "\n")
                    println("The file '$fileName' is tracked.")
                } else {
                    println("Can't find '$fileName'.")
                }
            }
        }

        "commit" -> {
            if (args.size < 2) {
                println("Message was not passed.")
                return
            }

            val commitMessage = args[1]

            if (!indexFile.exists() || indexFile.readText().isEmpty()) {
                println("Nothing to commit.")
                return
            }

            val filesToCommit = indexFile.readLines().map { it.trim() }.filter { it.isNotEmpty() }
            if (filesToCommit.isEmpty()) {
                println("Nothing to commit.")
                return
            }

            val commitHash = generateCommitHash(filesToCommit)

            val lastCommitDir = commitsDir.listFiles()?.maxByOrNull { it.name }
            val lastCommitHash = lastCommitDir?.name ?: ""

            if (commitHash == lastCommitHash) {
                println("Nothing to commit.")
                return
            }

            val newCommitDir = File(commitsDir, commitHash)
            newCommitDir.mkdir()

            filesToCommit.forEach { fileName ->
                val sourceFile = File(fileName)
                val destinationFile = File(newCommitDir, fileName)
                sourceFile.copyTo(destinationFile)
            }

            logFile.appendText("commit $commitHash\nAuthor: $currentUsername\n$commitMessage\n\n")

            println("Changes are committed.")
        }

        "log" -> {
            if (!logFile.exists() || logFile.readText().isEmpty()) {
                println("No commits yet.")
            } else {
                println(logFile.readText().trim().split("\n\n").reversed().joinToString("\n\n"))
            }
        }

        "checkout" -> {
            if (args.size < 2) {
                println("Commit id was not passed.")
                return
            }

            val commitId = args[1]
            val commitDir = File(commitsDir, commitId)

            if (commitDir.exists()) {
                commitDir.listFiles()?.forEach { file ->
                    val destinationFile = File(file.name)
                    file.copyTo(destinationFile, overwrite = true)
                }
                println("Switched to commit $commitId.")
            } else {
                println("Commit does not exist.")
            }
        }

        else -> {
            if (command in commands) {
                println(commands[command])
            } else {
                println("'$command' is not a SVCS command.")
            }
        }
    }
}

fun generateCommitHash(files: List<String>): String {
    val digest = MessageDigest.getInstance("SHA-1")
    files.forEach { fileName ->
        val file = File(fileName)
        if (file.exists()) {
            digest.update(file.readBytes())
        }
    }
    return digest.digest().joinToString("") { "%02x".format(it) }
}
