import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val channel = Channel<String>(Channel.UNLIMITED)

    repeat(2) {
        sender(channel)
        GlobalScope.launch { receiver(channel) }
    }
}

suspend fun sender(channel: Channel<String>) {
    readln().let { channel.send(it) }
}

suspend fun receiver(channel: Channel<String>) {
    println("Message received: ${channel.receive()}")
}
