class Storage {
    var productCount = 0

    @Synchronized
    fun addProduct() {
        productCount++
    }
}
