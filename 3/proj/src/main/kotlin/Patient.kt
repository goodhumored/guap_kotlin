

class Patient(val name: String) {

    private var number: Int = 0;

    fun setNumber(number: Int) {
        println("Пациент ${name} получил номер: ${number}")
        this.number = number;
    }

    fun getNumber(): Int {
        return number;
    }

    override fun toString(): String {
        return "$name[$number]"
    }
}