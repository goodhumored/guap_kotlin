fun main(args: Array<String>) {
    // делаем ствол
    val trunk = Branch(10f, 1f)
    // делаем ветки
    val branches = mutableListOf<Branch>()
    for (i in 1..3) {
        branches.add(Branch(1f, .1f, trunk))
    }

    // делаем дерево
    val tree = Tree("Берёза", trunk, branches)

    // выводим информацию о дереве
    println("Дерево до цветения:")
    println(tree)
    println(tree.branches)

    // делаем листья
    tree.bloom()

    // выводим информацию о дереве
    println("Дерево после цветения:")
    println(tree)
    println(tree.branches)

    // делаем листья жёлтыми
    tree.turnLeavesYellow()

    // выводим информацию о дереве
    println("Дерево после желтения:")
    println(tree)
    println(tree.branches)

    // сбрасываем листья
    tree.dropLeaves()

    // выводим информацию о дереве
    println("Дерево после сброса листьев:")
    println(tree)
    println(tree.branches)

    // замораживаем дерево
    tree.frostbite()

    // выводим информацию о дереве
    println("Дерево после заморозки:")
    println(tree)
    println(tree.branches)
}