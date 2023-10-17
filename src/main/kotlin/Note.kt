data class Note(override val name: String, val text: String):File {
    val archiveList = mutableListOf<Archive>()
    override fun toString(): String {
        return "$name\n\n$text"
    }
}
