import kotlin.math.max

data class Note(override val name: String, val text: String) : File {
    val archiveList = mutableListOf<Archive>()
    override fun toString(): String {
        return """
            ${"-".repeat(max(text.length, name.length) + 2)}
             $name
            ${"-".repeat(max(text.length, name.length) + 2)}
            
             $text
            
            ${"-".repeat(max(text.length, name.length) + 2)}
            
            Для возврата в меню нажмите ENTER
        """.trimIndent()
    }
}
