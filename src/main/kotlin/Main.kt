import java.util.Scanner

private val scanner = Scanner(System.`in`)
private const val readPrefix = "> "

fun main() {
    val archiveList = mutableListOf<Archive>()
    val menu = Menu()
    var menuState = MenuState.ARCHIVE_LIST
    var selectedArchiveIndex = -1
    var selectedNoteIndex = -1

    fun renderNoteMenu() = menu.render(archiveList[selectedArchiveIndex].notes, "Заметку")

    while (true) {
        when (menuState) {
            MenuState.ARCHIVE_LIST -> menu.render(archiveList, "Архив")
            MenuState.NOTES_LIST -> renderNoteMenu()
            MenuState.NOTE_VIEW -> {
                if (selectedArchiveIndex >= 0 && selectedNoteIndex >= 0) {
                    println(archiveList[selectedArchiveIndex].notes[selectedNoteIndex])
                    scanner.nextLine()
                    renderNoteMenu()
                    selectedNoteIndex = -1
                    menuState = MenuState.NOTES_LIST
                }
            }

            MenuState.CREATE_ARCHIVE -> {
                println("Введите название архива")
                val name = readWithPrefix()

                if (name.trim().isEmpty()) {
                    println("Название не может быть пустым!")
                    continue
                }

                archiveList.add(Archive(name, mutableListOf()))
                menuState = MenuState.ARCHIVE_LIST
                continue
            }

            MenuState.CREATE_NOTE -> {
                println("Введите название заметки")
                val name = readWithPrefix()

                if (name.trim().isEmpty()) {
                    println("Название не может быть пустым!")
                    continue
                }

                println("Введите текст заметки")
                val text = readWithPrefix()

                if (text.trim().isEmpty()) {
                    println("Содержание не может быть пустым!")
                    continue
                }

                archiveList[selectedArchiveIndex].notes.add(Note(name, text))
                menuState = MenuState.NOTES_LIST
                continue
            }
        }

        val selectedIndex = readWithPrefix().toIntOrNull()
        when {
            selectedIndex == null -> {
                println("Номер пункта должен являться числом")
            }

            selectedIndex < 0 || selectedIndex > menu.menuItems.lastIndex -> {
                println("Такого номера пункта не существует")
            }

            selectedIndex == 0 -> menuState = when (menuState) {
                MenuState.NOTES_LIST -> MenuState.CREATE_NOTE
                MenuState.ARCHIVE_LIST -> MenuState.CREATE_ARCHIVE
                else -> throw IllegalArgumentException()
            }

            selectedIndex < menu.menuItems.lastIndex -> {
                menuState = when (menuState) {
                    MenuState.ARCHIVE_LIST -> {
                        selectedArchiveIndex = selectedIndex - 1
                        MenuState.NOTES_LIST
                    }
                    MenuState.NOTES_LIST -> {
                        selectedNoteIndex = selectedIndex - 1
                        MenuState.NOTE_VIEW
                    }
                    else -> throw IllegalArgumentException()
                }
            }

            selectedIndex == menu.menuItems.lastIndex -> {
                menuState = when (menuState) {
                    MenuState.NOTES_LIST -> {
                        selectedArchiveIndex = -1
                        MenuState.ARCHIVE_LIST
                    }

                    MenuState.ARCHIVE_LIST -> return
                    else -> throw IllegalArgumentException()
                }
            }
        }
    }
}

private fun readWithPrefix(): String {
    print(readPrefix)
    return scanner.nextLine()
}

