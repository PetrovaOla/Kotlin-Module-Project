import java.util.Scanner

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val archiveList = mutableListOf<Archive>()
    val menu = Menu()
    var menuState = MenuState.ARCHIVE
    var selectedArchiveIndex = -1
    var selectedNotesIndex = -1

    while (true) {
        when (menuState) {
            MenuState.ARCHIVE -> menu.render(archiveList, "Архив")
            MenuState.NOTES -> menu.render(archiveList[selectedArchiveIndex].notes, "Заметку")
            MenuState.VIEW -> {
                if (selectedArchiveIndex >= 0 && selectedNotesIndex >= 0) {
                    println(archiveList[selectedArchiveIndex].notes[selectedNotesIndex])
                    scanner.nextLine()
                    MenuState.NOTES
                }
            }

            MenuState.CREATE -> {
                when {
                    selectedArchiveIndex < 0 -> {
                        println("Введите название архива")
                        val name = scanner.nextLine()
                        archiveList.add(Archive(name, mutableListOf()))
                        menuState = MenuState.ARCHIVE
                        continue
                    }

                    selectedArchiveIndex >= 0 -> {
                        println("Введите название записки")
                        val name = scanner.nextLine()
                        println("Введите текст записки")
                        val text = scanner.nextLine()
                        archiveList[selectedArchiveIndex].notes.add(Note(name, text))
                        menuState = MenuState.NOTES
                        continue
                    }
                }

            }
        }
        val selectedIndex = scanner.nextLine().toInt()
        when {
            selectedIndex == 0 -> menuState = MenuState.CREATE
            selectedIndex < menu.menuItems.lastIndex-> {
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
                when (menuState) {
                    MenuState.NOTES -> menuState = MenuState.ARCHIVE
                    else -> return
                }
            }
        }
    }
}
