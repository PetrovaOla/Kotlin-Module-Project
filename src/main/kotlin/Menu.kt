class Menu {
    private val menuFormatter = "%d. %s"
    val menuItems = mutableListOf<MenuItem>()
    fun render(list: List<File>, fileType:String) {

        menuItems.clear()

        menuItems.add(MenuItem("Создать $fileType"))
        menuItems.addAll(list.map { MenuItem(it.name) })
        menuItems.add(MenuItem("Выход"))

        menuItems.forEachIndexed { index, archiveMenuItem ->
            println(String.format(menuFormatter, index, archiveMenuItem.title))
        }
    }



}