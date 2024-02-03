import java.awt.Color
import java.awt.Dimension
import java.awt.Insets
import javax.swing.*
import javax.swing.table.DefaultTableModel
import kotlin.math.pow


var argument: Double = 0.0;

var selectedWay: Int = 1;

var startArgument: Double = 0.0;
var endArgument: Double = 0.0;
var step: Double = 0.0;

val tableModel = DefaultTableModel(arrayOf("x", "Эталон", "Результ"), 0);
val table = JTable(tableModel)
val tablePane = JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)

val way1Button = JRadioButton("Ввод одного аргумента", true)
val way2Button = JRadioButton("Ввод диапазона")
val maclaurinFormulaLabel = JLabel("Формула разложения функции в ряд Маклорена: (-1)^n*x^n")
val maclaurinResultLabel = JLabel("Результат с разложением: ")
val maclaurinResult = JLabel()
val referenceResultLabel = JLabel("Эталонный результат: ")
val referenceResult = JLabel()
val calculateButton = JButton("Вычислить")
val setDefaultButton = JButton("По умолч.")
val closeBtn = JButton("x")

val precisionLabel = JLabel("Точность вычислений (знаков после запятой): ")
val precisionInput = JTextField()

val way1Pane = JPanel()
val way2Pane = JPanel()

val argumentLabel = JLabel("Аргумент функции: ")
val argumentValueLabel = JLabel()
val argumentInput = JScrollBar(0, 0, 1, -9999, 10000)

val startArgumentLabel = JLabel("Первый аргумент: ")
val startArgumentValueLabel = JLabel()
val startArgumentInput = JScrollBar(0, 0, 1, -9999, 10000)

val stepArgumentLabel = JLabel("Шаг: ")
val stepArgumentValueLabel = JLabel()
val stepArgumentInput = JScrollBar(0, 0, 1, -9999, 10000)

val endArgumentLabel = JLabel("Последний аргумент: ")
val endArgumentValueLabel = JLabel()
val endArgumentInput = JScrollBar(0, 0, 1, -9999, 10000)

class MainWindow(title: String) : JFrame() {
    init {
        setTitle(title)

        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(500, 520)
        setLocationRelativeTo(null)

        val panel = JPanel()
        panel.layout = null
        way1Pane.layout = null
        way2Pane.layout = null

        closeBtn.setBounds(470, 10, 20, 20)
        closeBtn.font = closeBtn.font.deriveFont(12.0f)
        closeBtn.margin = Insets(0, 0, 0, 0);
        closeBtn.foreground = Color.RED
        closeBtn.isFocusPainted = false

        closeBtn.addActionListener {
            dispose()
        }

        maclaurinFormulaLabel.setBounds(10, 10, 400, 25)

        precisionLabel.setBounds(10, 50, 400, 25);
        precisionInput.setBounds(310, 50, 50, 25)

        way1Button.setBounds(10, 90, 200, 25);
        way1Button.addActionListener { selectedWay = 1 }
        way1Button.addActionListener { way2Button.isSelected = false }
        way1Button.addActionListener { way2Pane.isVisible = false }
        way1Button.addActionListener { way1Pane.isVisible = true }
        way2Button.setBounds(220, 90, 200, 25);
        way2Button.addActionListener { selectedWay = 2 }
        way2Button.addActionListener { way1Button.isSelected = false }
        way2Button.addActionListener { way1Pane.isVisible = false }
        way2Button.addActionListener { way2Pane.isVisible = true }

        way1Pane.setBounds(0, 130, 500, 300)
        argumentLabel.setBounds(10, 10, 130, 25);
        argumentValueLabel.setBounds(140, 10, 100, 25)
        argumentInput.setBounds(250, 10, 240, 25)
        argumentInput.accessibleContext.addPropertyChangeListener { val1 -> if (val1.newValue is Int) setArgumentValue(0.0001 * val1.newValue as Int) }
        maclaurinResultLabel.setBounds(10, 50, 400, 25)
        maclaurinResult.setBounds(350, 50, 400, 25)
        referenceResultLabel.setBounds(10, 90, 400, 25)
        referenceResult.setBounds(350, 90, 400, 25)

        way2Pane.setBounds(0, 130, 500, 300)
        startArgumentLabel.setBounds(10, 10, 130, 25);
        startArgumentValueLabel.setBounds(140, 10, 100, 25)
        startArgumentInput.setBounds(250, 10, 240, 25)
        startArgumentInput.accessibleContext.addPropertyChangeListener { val1 -> if (val1.newValue is Int) setStartArgumentValue(0.0001 * val1.newValue as Int) }
        endArgumentLabel.setBounds(10, 50, 150, 25);
        endArgumentValueLabel.setBounds(160, 50, 100, 25)
        endArgumentInput.setBounds(250, 50, 240, 25)
        endArgumentInput.accessibleContext.addPropertyChangeListener { val1 -> if (val1.newValue is Int) setEndArgumentValue(0.0001 * val1.newValue as Int) }
        stepArgumentLabel.setBounds(10, 90, 130, 25);
        stepArgumentValueLabel.setBounds(140, 90, 100, 25)
        stepArgumentInput.setBounds(250, 90, 240, 25)
        stepArgumentInput.accessibleContext.addPropertyChangeListener { val1 -> if (val1.newValue is Int) setStepArgumentValue(0.0001* val1.newValue as Int) }

        tablePane.setBounds(10, 130, 480, 150)
        tablePane.preferredSize = Dimension(400, 150)
        table.setBounds(0, 0, 480, 200)

        calculateButton.setBounds(10, 460, 120, 25)
        calculateButton.addActionListener {
            val precisionText = precisionInput.text;
            val precision: Double;
            try {
                precision =  precisionText.toDouble();
                referenceResult.text = calculateReference(argument).toString()
                if (selectedWay == 1) {
                    maclaurinResult.text = calculateMaclaurin(argument, 10.0.pow(-precision)).toString()
                } else {
                    if (startArgument > endArgument) {
                        throw IllegalArgumentException("Начальный аргумент должен быть больше конечного")
                    }
                    tableModel.setNumRows(0);
                    var i = startArgument;
                    while (i  < endArgument){
                        val reference = calculateReference(i).toString()
                        val res = calculateMaclaurin(i, 10.0.pow(-precision)).toString()
                        tableModel.addRow(arrayOf(i, reference, res))
                        i += step
                    }
                }

            } catch (err: NumberFormatException) {
                JOptionPane.showMessageDialog(this, "Введено не число");
            } catch (err: IllegalArgumentException) {
                JOptionPane.showMessageDialog(this, err.message);
            }
        }

        setDefaultButton.setBounds(140, 460, 100, 25)
        setDefaultButton.addActionListener { setDefaults() }

        panel.add(closeBtn)
        panel.add(maclaurinFormulaLabel)

        way1Pane.add(maclaurinResultLabel)
        way1Pane.add(maclaurinResult)

        way1Pane.add(referenceResultLabel)
        way1Pane.add(referenceResult)

        panel.add(precisionLabel)
        panel.add(precisionInput)

        panel.add(way1Button)
        panel.add(way2Button)

        way1Pane.add(argumentLabel)
        way1Pane.add(argumentValueLabel)
        way1Pane.add(argumentInput)

        way2Pane.add(startArgumentLabel)
        way2Pane.add(startArgumentValueLabel)
        way2Pane.add(startArgumentInput)
        way2Pane.add(endArgumentLabel)
        way2Pane.add(endArgumentValueLabel)
        way2Pane.add(endArgumentInput)
        way2Pane.add(stepArgumentLabel)
        way2Pane.add(stepArgumentValueLabel)
        way2Pane.add(stepArgumentInput)
        way2Pane.add(tablePane)
//        tablePane.add(table)

        panel.add(way1Pane)
        panel.add(way2Pane)

        panel.add(calculateButton)
        panel.add(setDefaultButton)

        contentPane = panel
    }
}

fun setArgumentValue(newValue: Double) {
    argumentValueLabel.text = newValue.toString();
    argument = newValue;
}

fun setStepArgumentValue(newValue: Double) {
    stepArgumentValueLabel.text = newValue.toString();
    step = newValue;
}

fun setStartArgumentValue(newValue: Double) {
    startArgumentValueLabel.text = newValue.toString();
    startArgument = newValue;
}

fun setEndArgumentValue(newValue: Double) {
    endArgumentValueLabel.text = newValue.toString();
    endArgument = newValue;
}

fun setDefaults() {
    argumentInput.value = 0
    precisionInput.text = ""
    referenceResult.text = ""
    maclaurinResult.text = ""
    startArgumentInput.value = 0
    endArgumentInput.value = 0
    stepArgumentInput.value = 0
    setArgumentValue(0.0);
    setStartArgumentValue(0.0)
    setStepArgumentValue(0.0)
    setEndArgumentValue(0.0)
}