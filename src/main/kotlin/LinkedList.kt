import java.util.*

data class Node<T>(var value: T, var next: Node<T>? = null) {
    override fun toString(): String {
        return if (next != null) {
            "$value -> ${next.toString()}"
        } else {
            "$value"
        }
    }
}

class LinkedList<T> : Iterable<T>, Collection<T>, MutableIterable<T>, MutableCollection<T> {
    private var head: Node<T>? = null
    private var tail: Node<T>? = null

    override var size = 0
    override fun clear() {
        head = null
        tail = null
        size = 0
    }

    override fun addAll(elements: Collection<T>): Boolean {
        for (element in elements) {
            append(element)
        }
        return true
    }

    override fun add(element: T): Boolean {
        append(element)
        return true
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        for (searched in elements) {
            if (!contains(searched)) return false
        }
        return true
    }

    override fun contains(element: T): Boolean {
        for (item in this) {
            if (item == element) return true
        }
        return false
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    override fun toString(): String {
        return if (isEmpty()) {
            "Empty List"
        } else {
            head.toString()
        }
    }

    fun push(value: T): LinkedList<T> {
        head = Node(value = value, next = head)
        if (tail == null) {
            tail = head
        }
        size++
        return this
    }

    fun append(value: T) {
        if (isEmpty()) {
            push(value)
            return
        }
        tail?.next = Node(value = value)
        tail = tail?.next
        size++
    }

    fun nodeAt(index: Int): Node<T>? {
        var currentNode = head
        var currentIndex = 0
        while (currentNode != null && currentIndex < index) {
            currentNode = currentNode.next
            currentIndex++
        }
        return currentNode
    }

    fun insert(value: T, afterNode: Node<T>): Node<T> {
        if (tail == afterNode) {
            append(value)
            return tail!!
        }
        val newNode = Node(value = value, next = afterNode.next)
        afterNode.next = newNode
        size++
        return newNode
    }

    fun pop(): T? {
        if (!isEmpty()) size--
        val result = head?.value
        head = head?.next
        if (isEmpty()) {
            tail = null
        }
        return result
    }

    fun removeLast(): T? {
        val head = head ?: return null
        if (head.next == null) return pop()
        size--
        var prev = head
        var current = head
        var next = current.next
        while (next != null) {
            prev = current
            current = next
            next = current.next
        }
        prev.next = null
        tail = prev
        return current.value
    }

    fun removeAfter(node: Node<T>): T? {
        val result = node.next?.value
        if (node.next == tail) tail = node
        if (node.next != null) {
            size--
        }
        node.next?.next == node.next
        return result
    }

    override fun iterator(): MutableIterator<T> {
        return LinkedListIterator(this)
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        var result = false
        val iterator = this.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (!elements.contains(item)) {
                iterator.remove()
                result = true
            }
        }
        return result
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        var result = false
        for (item in elements) {
            result = remove(item) || result
        }
        return result
    }

    override fun remove(element: T): Boolean {
        val iterator = iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item == element) {
                iterator.remove()
                return true
            }
        }
        return false
    }
}

class LinkedListIterator<T>(private val list: LinkedList<T>) : Iterator<T>, MutableIterator<T> {
    private var index = 0
    override fun hasNext(): Boolean {
        return index < list.size
    }

    override fun remove() {
        if (index == 1) {
            list.pop()
        } else {
            val prevNode = list.nodeAt(index - 2) ?: return
            list.removeAfter(prevNode)
            lastNode = prevNode
        }
        index--
    }

    private var lastNode: Node<T>? = null
    override fun next(): T {
        if (index >= list.size) throw IndexOutOfBoundsException()
        lastNode = if (index == 0) {
            list.nodeAt(0)
        } else {
            lastNode?.next
        }
        index++
        return lastNode!!.value
    }
}

fun main() {
    "Elementary data structures".also { println(it.uppercase(Locale.getDefault())) }
    "\n# creating and linking nodes".also { println(it) }
    val node1 = Node(value = 1)
    val node2 = Node(value = 2)
    val node3 = Node(value = 3)

    node1.next = node2
    node2.next = node3

    println(node1)

    "\n# pushing".also {
        println(it)
    }
    val list = LinkedList<Int>()
    list.push(4).push(3).push(2).push(1)
    println(list)

    "\n# appending".also {
        println(it)
    }
    list.apply {
        append(5)
        append(6)
        append(7)
    }
    println(list)

    "\n# inserting at particular index".also {
        println(it)
    }
    "before inserting: $list".also {
        println(it)
    }
    var middleNode = list.nodeAt(1)!!
    for (i in 1..5) {
        middleNode = list.insert(-1 * i, middleNode)
    }
    "after inserting the negatives: $list".also {
        println(it)
    }

    "\n# popping".also { println(it) }
    "Before popping list: $list".also { println(it) }
    val poppedValue = list.pop()
    "After popping list: $list".also { println(it) }
    "popped value: $poppedValue".also { println(it) }

    "\n# removing the last node".also { println(it) }
    "before removing last node: $list".also { println(it) }
    val removedValue = list.removeLast()
    "after removing the last node: $list".also { println(it) }
    "Removed value: $removedValue".also { println(it) }

    "\n# removing a node after a particular node".also { println(it) }
    "before removing at a particular index: $list".also { println(it) }
    val index = 1
    val node = list.nodeAt(index - 1)!!
    val removedNode = list.removeAfter(node)
    "after removing at index $index: $list".also { println(it) }
    "removed value: $removedNode".also { println(it) }

    "\n# printing doubles".also { println(it) }
    println(list)
    for (item in list) println("Double: ${item * 2}")

    "\n# removing elements".also { println(it) }
    list.also {
        println(it)
        it.remove(1)
        println(it)
    }

    "\n# retaining elements".also { println(it) }
    list.also {
        println(it)
        it.retainAll(listOf(3, 4, 5))
        println(it)
    }

    "\n# remove all elements".also { println(it) }
    list.also {
        println(it)
        it.removeAll(listOf(2, 4, 5))
        println(it)
    }
//    stopped at reverse a linked list and merging linked lists
}