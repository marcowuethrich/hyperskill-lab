class UserInfo(val name: String, val age: Int, val userName: String, val email: String) {
    override fun equals(other: Any?): Boolean {
        if (other !is UserInfo) return false
        return this.name == other.name
                && this.age == other.age
                && this.userName == other.userName
                && this.email == other.email
    }
}
