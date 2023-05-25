import java.util.*

fun main() {
//    Time complexity
    println("# time complexity".uppercase(Locale.getDefault()))
    listOf("Ricky", "Sensei").also {
        checkFirst(it)
        printName(it)
    }
    multiplicationBoard(5)

    val numbers =  listOf(9,8,7,6,5,4,2,3,4,3,7,8,7,2)
    pseudoBinaryContains(5,numbers)

    println("\n# others include quasilinear ,polynomial time, exponential time, factorial time")
    numbers.also {
        printSorted(numbers)
    }
}

fun checkFirst(name: List<String>) {
    println("\n# constant time algorithm - O(1)")
//    same running time regardless of the size of the input.
    if (name.firstOrNull() != null) {
        println(name.first())
    } else {
        println("incorrect name!")
    }
}

fun printName(names: List<String>) {
    println("\n# linear time - O(n)")
//    time increases by increase in the amount of data
    for (name in names) {
        println(name)
    }
}

fun multiplicationBoard(size: Int) {
    println("\n# quadratic time/n squared - O(n^2)")
    /*
    time complexity refers to an
    algorithm that takes time proportional to the square of the input size.
    */
    for (number in 1..size) {
        print(" | ")
        for (otherNumber in 1..size) {
            print("$number x $otherNumber = ${number * otherNumber} | ")
        }
        println()
    }
}

fun pseudoBinaryContains(value: Int, numbers: List<Int>) {
    println("\n# logarithmic time - O(log n)")
    /*
    As input data increases, the time it takes to execute the algorithm increases at a
    slower rate.
    */
    if (numbers.isEmpty()) println("$value not found")
    val middleIndex = numbers.size / 2
    if (value <= numbers[middleIndex]) {
        for (index in 0..middleIndex) {
            if (numbers[index] == value) {
                println("$value found")
            }
        }
    } else {
        for (index in middleIndex until numbers.size) {
            if (numbers[index] == value) {
                println("$value found")
            }
        }
    }
//    println("$value not found")
}
fun printSorted(numbers: List<Int>){
    println("\n# space complexity - O(1)".uppercase(Locale.getDefault()))
    if (numbers.isEmpty()) return

    var currentCount = 0
    var minValue = Int.MIN_VALUE

    for(value in numbers){
        if (value == minValue){
            println(value)
            currentCount+=1
        }
    }
    while (currentCount<numbers.size){
        var currentValue = numbers.max()
        for (value in numbers){
            if (value < currentValue && value > minValue){
                currentValue = value
            }
        }

        for (value in numbers){
            if (value == currentValue){
                print("$value, ")
                currentCount +=1
            }
        }
        minValue = currentValue
    }
}