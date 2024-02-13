package leet_code.merge_string_alternatively

/*
Example 1:

Input: word1 = "abc", word2 = "pqr"
Output: "apbqcr"
Explanation: The merged string will be merged as so:
word1:  a   b   c
word2:    p   q   r
merged: a p b q c r

Example 2:

Input: word1 = "ab", word2 = "pqrs"
Output: "apbqrs"
Explanation: Notice that as word2 is longer, "rs" is appended to the end.
word1:  a   b
word2:    p   q   r   s
merged: a p b q   r   s

Example 3:

Input: word1 = "abcd", word2 = "pq"
Output: "apbqcd"
Explanation: Notice that as word1 is longer, "cd" is appended to the end.
word1:  a   b   c   d
word2:    p   q
merged: a p b q c   d
*/

fun main() {
    println("Hello")
    mergeAlternately("abc", "pqr").also {
        println(it)
    }
}

fun mergeAlternately(word1: String, word2: String): String {
    var newString = ""
    for (l1 in word1) {
        newString += l1.toString()
        for (l2 in word2) {
            newString += l2.toString()
        }
    }
    return newString
}
