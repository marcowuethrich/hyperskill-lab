// complete the definition of the ship 
class Ship(val name: String, val ammunition: Int) {

    override fun hashCode(): Int = ammunition

    override fun equals(other: Any?): Boolean {
        if (other !is Ship) return false
        return this.ammunition == other.ammunition
    }
}
