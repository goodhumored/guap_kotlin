import java.awt.EventQueue


private fun createAndShowGUI() {
    val frame = MainWindow("Simple")
    frame.isVisible = true
}

fun main() {
    EventQueue.invokeLater(::createAndShowGUI)
}