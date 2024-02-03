import java.awt.EventQueue

// Создаём и показываем главное окно
private fun createAndShowGUI() {
    val frame = MainWindow("Simple")
    frame.isVisible = true
}

// Точка входа
fun main() {
    EventQueue.invokeLater(::createAndShowGUI)
}