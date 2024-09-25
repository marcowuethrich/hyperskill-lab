class B {
    lateinit var b: String

    fun checkB(): Boolean = ::b.isInitialized
}
