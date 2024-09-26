//import kotlinx.coroutines.*

// Define a proper scope here
// and make sure it doesn't propagate exception
// but prints exception's `.message` to console
val exceptionHandler = CoroutineExceptionHandler { _, exception ->
    println("Caught an exception: ${exception.message}")
}
val mainScope = CoroutineScope(Dispatchers.Default + exceptionHandler)

suspend fun main() {
    // we load data in the main scope
    // and wait for it to finish explicitly
    // so there is no need to call 'runBlocking'
    // also no need to modify this code
    val job1 = mainScope.loadDataButFail()
    val job2 = mainScope.loadData()
    joinAll(job1, job2)
}
