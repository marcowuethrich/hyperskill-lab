fun printIfDaemon(thread: Thread) {
    if (thread.isDaemon) println("daemon") else println("not daemon")
}
