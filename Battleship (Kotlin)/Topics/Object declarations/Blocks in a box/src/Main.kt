class Block(val color: String) {
    object BlockProperties {
        var length: Int = 6
        var width: Int = 4

        fun blocksInBox(length: Int, width: Int) = (length / BlockProperties.length) * (width / BlockProperties.width)
    }
}
