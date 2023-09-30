class Tree(val name: String, val trunk: Branch, val branches: List<Branch>) {
    fun dropLeaves() {
        trunk.dropLeaves()
        branches.forEach { it.dropLeaves() }
    }

    fun turnLeavesYellow() {
        trunk.turnLeavesYellow()
        branches.forEach { it.turnLeavesYellow() }
    }

    fun bloom() {
        trunk.growLeaves()
        branches.forEach { it.growLeaves() }
    }

    fun frostbite() {
        trunk.frostbite()
        branches.forEach { it.frostbite() }
    }

    override fun toString(): String {
        return "Дерево: $name, " +
                "толщина ствола: ${trunk.thickness}, " +
                "высота: ${trunk.length}, " +
                "количество веток: ${branches.size}, " +
                "количество листьев: ${branches.sumOf { it.leavesCount() }} "
    }

    override fun equals(other: Any?): Boolean {
        if (other is Tree) {
            return name == other.name && trunk == other.trunk && branches == other.branches
        }
        return false
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + trunk.hashCode()
        result = 31 * result + branches.hashCode()
        return result
    }
}