import java.awt.Color
import java.awt.Dimension
import java.awt.Insets
import javax.swing.*
import javax.swing.table.DefaultTableModel
import kotlin.math.pow

// Глобальная переменная способа ввода данных
var selectedWay: Int = 1;

// Глобальная переменная для аргумента
var argument: Double = 0.0;

// Переменные для ввода диапазона
var startArgument: Double = 0.0;
var endArgument: Double = 0.0;
var step: Double = 0.0;

// Таблица для диапазона
val tableModel = DefaultTableModel(arrayOf("x", "Эталон", "Результ"), 0);
val table = JTable(tableModel)
val tablePane = JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)

// Кнопки выбора варианта ввода
val way1Button = JRadioButton("Ввод одного аргумента", true)
val way2Button = JRadioButton("Ввод диапазона")

// Элементы интерфейса
val maclaurinFormulaLabel = JLabel("Формула разложения функции в ряд Маклорена: (-1)^n*x^n")

// Элементы интерфейса ввода единого аргумента
val maclaurinResultLabel = JLabel("Результат с разложением: ")
val maclaurinResult = JLabel()
val referenceResultLabel = JLabel("Эталонный результат: ")
val referenceResult = JLabel()

// кнопка инициализации вычислений
val calculateButton = JButton("Вычислить")
val setDefaultButton = JButton("По умолч.")

// кнопка закрытия приложения
val closeBtn = JButton("x")

// поле для ввода точности
val precisionLabel = JLabel("Точность вычислений (знаков после запятой): ")
val precisionInput = JTextField()

// панели для способов ввода
val way1Pane = JPanel()
val way2Pane = JPanel()

// Элементы ввода диапазона аргументов
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

// главное окно
class MainWindow(title: String) : JFrame() {
    init {
        // заголовок
        setTitle(title)

        // выход по закрытию
        defaultCloseOperation = EXIT_ON_CLOSE

        // настройки окна
        setSize(500, 520)
        setLocationRelativeTo(null)

        // панели
        val panel = JPanel()
        panel.layout = null
        way1Pane.layout = null
        way2Pane.layout = null

        // размещение кнопки закрытия
        closeBtn.setBounds(470, 10, 20, 20)
        closeBtn.font = closeBtn.font.deriveFont(12.0f)
        closeBtn.margin = Insets(0, 0, 0, 0);
        closeBtn.foreground = Color.RED
        closeBtn.isFocusPainted = false

        // логика кнопки закрытия
        closeBtn.addActionListener {
            dispose()
        }

        // размещение остальных элементов интерфейса
        maclaurinFormulaLabel.setBounds(10, 10, 400, 25)

        precisionLabel.setBounds(10, 50, 400, 25);
        precisionInput.setBounds(310, 50, 50, 25)

        //размещение и логика кнопок выбора варианта ввода
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

        // Элементы ввода ввода одного аргумента
        way1Pane.setBounds(0, 130, 500, 300)
        argumentLabel.setBounds(10, 10, 130, 25);
        argumentValueLabel.setBounds(140, 10, 100, 25)
        argumentInput.setBounds(250, 10, 240, 25)
        argumentInput.accessibleContext.addPropertyChangeListener { val1 -> if (val1.newValue is Int) setArgumentValue(0.0001 * val1.newValue as Int) }
        maclaurinResultLabel.setBounds(10, 50, 400, 25)
        maclaurinResult.setBounds(350, 50, 400, 25)
        referenceResultLabel.setBounds(10, 90, 400, 25)
        referenceResult.setBounds(350, 90, 400, 25)

        // Элементы ввода ввода диапазона аргументов
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

        // панель таблицы 
        tablePane.setBounds(10, 130, 480, 150)
        tablePane.preferredSize = Dimension(400, 150)
        table.setBounds(0, 0, 480, 200)

        // размещение и логика кнопки вычислений
        calculateButton.setBounds(10, 460, 120, 25)
        calculateButton.addActionListener {
            val precisionText = precisionInput.text;
            val precision: Double;
            try {
                // пробуем перевести текст в double
                precision =  precisionText.toDouble();
                if (selectedWay == 1) {
                    // считаем и выводим результат эталонный
                    referenceResult.text = calculateReference(argument).toString()
                    // считаем и выводим результат по ряду
                    maclaurinResult.text = calculateMaclaurin(argument, 10.0.pow(-precision)).toString()
                } else {
                    // проверяем что аргументы норм
                    if (startArgument > endArgument) {
                        throw IllegalArgumentException("Начальный аргумент должен быть больше конечного")
                    }
                    // опустошаем таблицу
                    tableModel.setNumRows(0);
                    // проходим по диапазону и забиваем результаты и эталон в таблицу
                    var i = startArgument;
                    while (i  < endArgument){
                        val reference = calculateReference(i).toString()
                        val res = calculateMaclaurin(i, 10.0.pow(-precision)).toString()
                        tableModel.addRow(arrayOf(i, reference, res))
                        i += step
                    }
                }
            } catch (err: NumberFormatException) {
                // если текст не перевёлся показываем ошибку
                JOptionPane.showMessageDialog(this, "Введено не число");
            } catch (err: IllegalArgumentException) {
                // если плохие пргументы показываем ошибку
                JOptionPane.showMessageDialog(this, err.message);
            }
        }

        // кнопка по умолч
        setDefaultButton.setBounds(140, 460, 100, 25)
        setDefaultButton.addActionListener { setDefaults() }

        // добавляем элементы на панели
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
        panel.add(way1Pane)
        panel.add(way2Pane)
        panel.add(calculateButton)
        panel.add(setDefaultButton)

        contentPane = panel
    }
}

// функция чтоб поставить значение в лейбл и в переменную
fun setArgumentValue(newValue: Double) {
    argumentValueLabel.text = newValue.toString();
    argument = newValue;
}

// функция чтоб поставить значение в лейбл и в переменную
fun setStepArgumentValue(newValue: Double) {
    stepArgumentValueLabel.text = newValue.toString();
    step = newValue;
}

// функция чтоб поставить значение в лейбл и в переменную
fun setStartArgumentValue(newValue: Double) {
    startArgumentValueLabel.text = newValue.toString();
    startArgument = newValue;
}

// функция чтоб поставить значение в лейбл и в переменную
fun setEndArgumentValue(newValue: Double) {
    endArgumentValueLabel.text = newValue.toString();
    endArgument = newValue;
}

// функция которая все сбрасывает
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