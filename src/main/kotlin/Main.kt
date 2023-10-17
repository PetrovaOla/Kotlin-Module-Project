import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    val archiveList = mutableListOf<Archive>()
    val menu = Menu()
    var menuState = MenuState.ARCHIVE
    var selectedArchiveIndex = -1
    var selectedNotesIndex = -1

    fun renderNoteMenu() = menu.render(archiveList[selectedArchiveIndex].notes, "Заметку")

    while (true) {
        when (menuState) {
            MenuState.ARCHIVE -> menu.render(archiveList, "Архив")
            MenuState.NOTES -> renderNoteMenu()
            MenuState.VIEW -> {
                if (selectedArchiveIndex >= 0 && selectedNotesIndex >= 0) {
                    println(archiveList[selectedArchiveIndex].notes[selectedNotesIndex])
                    scanner.nextLine()
                    renderNoteMenu()
                    MenuState.NOTES
                }
            }

            MenuState.CREATE -> {
                when {
                    selectedArchiveIndex < 0 -> {
                        println("Введите название архива")
                        print("> ")
                        val name = scanner.nextLine()

                        if (name.trim().isEmpty()) {
                            println("Название не может быть пустым!")
                            continue
                        }

                        archiveList.add(Archive(name, mutableListOf()))
                        menuState = MenuState.ARCHIVE
                        continue
                    }

                    selectedArchiveIndex >= 0 -> {
                        println("Введите название заметки")
                        print("> ")
                        val name = scanner.nextLine()

                        if (name.trim().isEmpty()) {
                            println("Название не может быть пустым!")
                            continue
                        }

                        println("Введите текст заметки")
                        print("> ")
                        val text = scanner.nextLine()

                        if (text.trim().isEmpty()) {
                            println("Содержание не может быть пустым!")
                            continue
                        }

                        archiveList[selectedArchiveIndex].notes.add(Note(name, text))
                        menuState = MenuState.NOTES
                        continue
                    }
                }

            }
        }

        print("> ")
        val selectedIndex = scanner.nextLine().toIntOrNull()
        when {
            selectedIndex == null -> {
                println("Введён некорректный номер пункта")
            }

            selectedIndex < 0 || selectedIndex > menu.menuItems.lastIndex -> {
                println("Такого номера пункта не существует")
            }

            selectedIndex == 0 -> menuState = MenuState.CREATE
            selectedIndex < menu.menuItems.lastIndex -> {
                when (menuState) {
                    MenuState.ARCHIVE -> {
                        selectedArchiveIndex = selectedIndex - 1
                        menuState = MenuState.NOTES
                    }

                    MenuState.NOTES -> {
                        selectedNotesIndex = selectedIndex - 1
                        menuState = MenuState.VIEW
                    }

                    else -> {}
                }
            }

            selectedIndex == menu.menuItems.lastIndex -> {
                menuState = when (menuState) {
                    MenuState.NOTES -> {
                        selectedArchiveIndex = -1
                        MenuState.ARCHIVE
                    }
                    MenuState.VIEW -> MenuState.NOTES
                    MenuState.ARCHIVE -> return
                    else -> {throw IllegalArgumentException()}
                }
            }
        }
    }
}

