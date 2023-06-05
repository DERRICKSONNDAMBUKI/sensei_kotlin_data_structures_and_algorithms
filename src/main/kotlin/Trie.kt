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
    }
}

//extensions
fun Trie<Char>.insert(string: String) {
    insert(string.toList())
}

fun Trie<Char>.contains(string: String): Boolean {
    return contains(string.toList())
}