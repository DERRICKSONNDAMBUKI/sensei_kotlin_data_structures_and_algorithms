import kotlin.math.max

typealias Visitor<T> = (T) -> Unit

class BinaryNode<T>(private val value: T) {
    var leftChild: BinaryNode<T>? = null
    var rightChild: BinaryNode<T>? = null

    private fun diagram(node: BinaryNode<T>?, top: String = "", root: String = "", bottom: String = ""): String {
        return node?.let {
            if ((node.leftChild == null) && (node.rightChild == null)) {
                "$root${node.value}\n"
            } else {
                diagram(
                    node.rightChild,
                    "$top ",
                    "$top┌──",
                    "$top| "
                ) + root + "${node.value}\n" +
                        diagram(node.leftChild, "$bottom| ", "$bottom└──", bottom)
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

    val tree = seven
    tree.also { node ->
        println(node)
        println("\n# in-order traversal")
        println(node.traversalInOrder { println(it) })
        println("\n# pre-order traversal")
        println(node.traversePreOrderWithNull { println(it) })
        println("\n# post-order traversal")
        println(node.traversePostOrder { println(it) })
        println("\n# height")
        println(node.height(node))
        println("\n# serialization")
        println(node.serialize())
        println("\n# deserialization")
        val array = node.serialize(node)
        println(node.deserialize(array))
        println("\n# optimized deserialization")
//        println(node.deserializeOptimized(array)) // ricky has bugs
    }
}