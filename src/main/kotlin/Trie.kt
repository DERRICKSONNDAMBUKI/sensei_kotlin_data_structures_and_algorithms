class EnglishDictionary {
    private val words: ArrayList<String> = TODO()
    fun words(prefix: String) = words.filter {
        it.startsWith(prefix)
    }
}

class TrieNode<Key>(val key: Key?, var parent: TrieNode<Key>?) {
    val children: HashMap<Key, TrieNode<Key>> = HashMap()
    var isTerminating = false
}

class Trie<Key> {
    private val root = TrieNode<Key>(key = null, parent = null)

    fun insert(list: List<Key>) {
        var current = root
        list.forEach { element ->
            if (current.children[element] == null) {
                current.children[element] = TrieNode(element, current)
            }
            current = current.children[element]!!
        }
        current.isTerminating = true
    }

    fun contains(list: List<Key>): Boolean {
        var current = root
        list.forEach { element ->
            val child = current.children[element] ?: return false
            current = child
        }
        return current.isTerminating
    }
    // TODO: remove

    fun collections(prefix: List<Key>): List<List<Key>> {
        var current = root
        prefix.forEach { element ->
            val child = current.children[element] ?: return emptyList()
            current = child
        }
        return collections(prefix, current)
    }

    private fun collections(prefix: List<Key>, node: TrieNode<Key>?): List<List<Key>> {
        val results = mutableListOf<List<Key>>()
        if (node?.isTerminating == true) results.add(prefix)
        node?.children?.forEach { key, node ->
            results.addAll(collections(prefix + key, node))
        }
        return results
    }
}

fun main() {
    "Tries".also { println(it.uppercase()) }
    "\n# insert and contains".also {
        println(it)
        val trie = Trie<Char>()
        trie.insert("cute")

        if (trie.contains("cute")) {
            println("cute is the trie")
        }

        "\n# prefix matching".also {
            println(it)
            val trie = Trie<Char>().apply {
                insert("car")
                insert("card")
                insert("care")
                insert("cared")
                insert("cars")
                insert("carbs")
                insert("carapace")
                insert("cargo")
            }
            println("\n collections starting with \"car\"")
            val prefixWithCar = trie.collections("car")
            println(prefixWithCar)

            println("\n collections starting with \"care\"")
            val prefixWithCare = trie.collections("care")
            println(prefixWithCare)
        }
    }
}

//extensions
fun Trie<Char>.insert(string: String) {
    insert(string.toList())
}

fun Trie<Char>.contains(string: String): Boolean {
    return contains(string.toList())
}

fun Trie<Char>.collections(prefix: String): List<String> =
    collections(prefix.toList()).map { it.joinToString(separator = "") }
