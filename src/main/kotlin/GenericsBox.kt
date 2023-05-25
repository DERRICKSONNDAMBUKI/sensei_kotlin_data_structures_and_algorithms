class GenericsBox<T> {
    private var content: T? = null
    fun put(content: T?) {
        this.content = content
    }

    fun retrieve(): T? {
        return content
    }

    fun isEmpty(): Boolean {
        return content == null
    }
}

fun main() {
    val intBox = GenericsBox<Int>()
    intBox.run {
        put(5)
        println(retrieve())
    }

    val boolBox = GenericsBox<Boolean>()
    boolBox.also {
        it.put(true)
    }.run {
        isEmpty()
        retrieve().also {
            println(it)
        }
    }


}