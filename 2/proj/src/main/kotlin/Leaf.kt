class Leaf(private var initialBranch: Branch) {

    private var currentBranch: Branch? = initialBranch

    private var color = "зелёный"

    init {
        currentBranch?.addLeaf(this)
    }

    fun turnYellow() {
        color = "жёлтый"
    }

    fun fall() {
        currentBranch = null
    }

    override fun equals(other: Any?): Boolean {
        if (other is Leaf) {
            return currentBranch == other.currentBranch && color == other.color
        }
        return false
    }

    override fun hashCode(): Int {
        var result = currentBranch.hashCode()
        result = 31 * result + color.hashCode()
        return result
    }

    override fun toString(): String {
        return "Лист: цвет $color"
    }
}