//class TreeNode<T>(val value: T) {
//    private val children: MutableList<TreeNode<T>> = mutableListOf()
//
//    fun add(child: TreeNode<T>) = children.add(child)
//
//    fun forEachDepthFirst(visit: Visitor<T>) {
//        visit(this)
//        children.forEach { it.forEachDepthFirst(visit) }
//    }
//    // TODO: level-order traversal
//    // TODO: search
//}
//
//typealias Visitor<T> = (TreeNode<T>) -> Unit
//
//fun makeBeverageTree(): TreeNode<String> {
//    val tree = TreeNode("Beverages")
//    val hot = TreeNode("hot")
//    val cold = TreeNode("cold")
//
//    val tea = TreeNode("tea")
//    val coffee = TreeNode("coffee")
//    val chocolate = TreeNode("chocolate")
//
//    val blackTea = TreeNode("black tea")
//    val greenTea = TreeNode("green tea")
//    val chaiTea = TreeNode("chai")
//
//    val soda = TreeNode("soda")
//    val milk = TreeNode("milk")
//
//    val gingerAle = TreeNode("ginger ale")
//    val bitterLemon = TreeNode("bitter lemon")
//
//    tree.run {
//        add(hot)
//        add(cold)
//    }
//    hot.run {
//        add(tea)
//        add(coffee)
//        add(chocolate)
//    }
//    cold.run {
//        add(soda)
//        add(milk)
//    }
//    tea.run {
//        add(blackTea)
//        add(greenTea)
//        add(chaiTea)
//    }
//
//    soda.run {
//        add(gingerAle)
//        add(bitterLemon)
//    }
//    return tree
//}
//
//fun main() {
//    val tree = makeBeverageTree()
//    "Tree".also { println(it.uppercase()) }
//    "\n# depth first traversal visit".also { println(it) }
//    tree.forEachDepthFirst {
//        println(it.value)
//    }
//}