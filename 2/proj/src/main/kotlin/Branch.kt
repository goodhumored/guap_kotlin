class Branch(val length: Float, val thickness: Float, val parent: Branch? = null) {
    private val leaves: MutableList<Leaf> = mutableListOf();
    private var frostbitten = false

    fun addLeaf(leaf: Leaf) {
        leaves.add(leaf)
    }

    fun dropLeaves() {
        leaves.forEach { it.fall() }
        leaves.clear()
    }

    fun turnLeavesYellow() {
        leaves.forEach { it.turnYellow() }
    }

    fun growLeaves() {
        for (i in 1..10) {
            Leaf(this)
        }
    }

    fun leavesCount(): Int {
        return leaves.size
    }

    fun frostbite() {
        frostbitten = true
    }

    override fun toString(): String {
        val parentInfo = if (parent != null) {
            "ветка растёт из ветки длиной ${parent.length} и толщиной ${parent.thickness}"
        } else {
            "эта ветка - ствол"
        }

        val isFrostbitten = if (frostbitten) {
            "заиндевела"
        } else {
            "в норме"
        }

        val leavesInfo = if (leaves.size > 0) {
            "количество листьев: ${leaves.size}, листья: $leaves"
        } else {
            "листьев нет"
        }

        return "Ветка: $parentInfo, " +
                "длина: $length, " +
                "толщина: $thickness, " +
                "состояние: $isFrostbitten, " +
                leavesInfo
    }

    override fun equals(other: Any?): Boolean {
        if (other is Branch) {
            return length == other.length && thickness == other.thickness && leaves == other.leaves
        }
        return false
    }

    override fun hashCode(): Int {
        var result = length.hashCode()
        result = 31 * result + thickness.hashCode()
        result = 31 * result + leaves.hashCode()
        return result
    }
}