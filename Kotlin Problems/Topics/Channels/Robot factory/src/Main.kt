import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

suspend fun simulateFactory(): List<Int> {
    val items = mutableListOf<Int>()
    val channel = Channel<Int>() // Rendezvous channel

    val producerJob = GlobalScope.launch {
        repeat(10) {
            delay(100) // Simulate production time
            val item = it + 1 // Produce items with incrementing value (1 to 10)
            channel.send(item)
        }
    }

    val consumerJob = GlobalScope.launch {
        repeat(10) {
            val item = channel.receive() // Wait for item to be produced
            items.add(item)
        }
    }

    producerJob.join() // Wait for producer to finish
    consumerJob.join() // Wait for consumer to finish
    channel.close() // Close the channel after all items are processed

    return items
}
