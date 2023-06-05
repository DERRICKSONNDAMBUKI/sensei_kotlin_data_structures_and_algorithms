import kotlin.math.max

typealias Visitor<T> = (T) -> Unit

class BinaryNode<T>(var value: T) {
    var leftChild: BinaryNode<T>? = null
    var rightChild: BinaryNode<T>? = null

    private fun diagram(node: BinaryNode<T>?, top: String = "", root: String = "", bottom: String = ""): String {
        return node?.let {
            if ((node.leftChild == null) && (node.rightChild == null)) {
                "$root${node.value}\n"
            } else {
                diagram(
                    node.rightChild, "$top ", "$top┌──", "$top| "
                ) + root + "${node.value}\n" + diagram(node.leftChild, "$bottom| ", "$bottom└──", bottom)
            }
        } ?: "${root}null\n"
    }

    override fun toString() = diagram(this)


    fun traversalInOrder(visit: Visitor<T>) {
        leftChild?.traversalInOrder(visit)
        visit(value)
        rightChild?.traversalInOrder(visit)
    }

    fun traversePreOrderWithNull(visit: Visitor<T?>) { // ricky has bags
        visit(value)
        leftChild?.traversePreOrderWithNull(visit) ?: visit(null)
        rightChild?.traversePreOrderWithNull(visit) ?: visit(null)
    }

    fun traversePostOrder(visit: Visitor<T>) {
        leftChild?.traversePostOrder(visit)
        rightChild?.traversePostOrder(visit)
        visit(value)
    }

    fun height(node: BinaryNode<T>? = this): Int {
        return node?.let {
            1 + max(height(node.leftChild), height(node.rightChild))
        } ?: 1
    }

    fun serialize(node: BinaryNode<T> = this): MutableList<T?> {
        val list = mutableListOf<T?>()
        node.traversePreOrderWithNull { list.add(it) }
        return list
    }

    fun deserialize(list: MutableList<T?>): BinaryNode<T?>? {
        val rootValue = list.removeAt(list.size - 1) ?: return null
        val root = BinaryNode<T?>(rootValue)
        root.leftChild = deserialize(list)
        root.rightChild = deserialize(list)

        return root
    }

    fun deserializeOptimized(list: MutableList<T?>): BinaryNode<T?>? {
        return deserialize(list.asReversed())
    }

    val min: BinaryNode<T>
        get() = leftChild?.min ?: this
}


class BinarySearchTree<T : Comparable<T>> {
    private var root: BinaryNode<T>? = null
    override fun toString(): String = root?.toString() ?: "empty tree"

    fun insert(value: T) {
        root = insert(root, value)
    }

    private fun insert(node: BinaryNode<T>?, value: T): BinaryNode<T> {
        node ?: return BinaryNode(value)

        if (value < node.value) {
            node.leftChild = insert(node.leftChild, value)
        } else {
            node.rightChild = insert(node.rightChild, value)
        }
        return node
    }

    fun contains(value: T): Boolean {
        root ?: return false
        var found = false
        root?.traversalInOrder {
            if (value == it) found = true
        }
        return found
    }

    fun optimizedContains(value: T): Boolean {
        var current = root
        while (current != null) {
            if (current.value == value) return true

            current = if (value < current.value) current.leftChild else current.rightChild
        }
        return false
    }

    fun remove(value: T) {
        root = remove(root, value)
    }

    private fun remove(node: BinaryNode<T>?, value: T): BinaryNode<T>? {
        node ?: return null
        when {
            value == node.value -> {
                if (node.leftChild == null && node.rightChild == null) return null
                if (node.leftChild == null) return node.rightChild
                if (node.rightChild == null) return node.leftChild
                node.rightChild?.min?.value?.let {
                    node.value = it
                }
                node.rightChild = remove(node.rightChild, node.value)
            }

            value < node.value -> node.leftChild = remove(node.leftChild, value)
            else -> node.rightChild = remove(node.rightChild, value)
        }
        return node
    }

}

fun main() {
    val zero = BinaryNode(0)
    val one = BinaryNode(1)
    val five = BinaryNode(5)
    val seven = BinaryNode(7)
    val eight = BinaryNode(8)
    val nine = BinaryNode(9)

    seven.leftChild = one
    one.run {
        leftChild = zero
        rightChild = five
    }
    seven.rightChild = nine
    nine.leftChild = eight

    seven.also { tree ->
        println(tree)
        println("\n# in-order traversal")
        println(tree.traversalInOrder { println(it) })
        println("\n# pre-order traversal")
        println(tree.traversePreOrderWithNull { println(it) })
        println("\n# post-order traversal")
        println(tree.traversePostOrder { println(it) })
        println("\n# height")
        println(tree.height(tree))
        println("\n# serialization")
        println(tree.serialize())
        println("\n# deserialization")
        val array = tree.serialize(tree)
        println(tree.deserialize(array))
        println("\n# optimized deserialization")
//        println(tree.deserializeOptimized(array)) // ricky has bugs
    }


    "\n# building a BST".also {
        val bst = BinarySearchTree<Int>()
        (0..4).forEach {
            bst.insert(it)
        }
        println(it)
        println(bst)
    }

    val exampleTree = BinarySearchTree<Int>().apply {
        insert(3)
        insert(1)
        insert(4)
        insert(0)
        insert(2)
        insert(5)
    }

    "\n# building a new bst".also {
        println(it)
        println(exampleTree)
    }

    "\n# finding a node".also {
        println(it)
        if (exampleTree.contains(5)) println("found 5!") else println("couldn't find 5")
    }
    "\n# finding a node - optimized contains".also {
        println(it)
        if (exampleTree.optimizedContains(8)) println("found 5!") else println("couldn't find 5")
    }
    "\n# removing a node".also {
        println(it)
        println("Tree before removal: ")
        println(exampleTree)
        exampleTree.remove(3)
        println("Tree after removing root (3):")
        println(exampleTree)
    }
}

