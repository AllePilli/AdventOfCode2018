import kotlin.math.abs
import kotlin.math.min

/**
 * @return the frequency of a given Character in a String
 */
fun String.frequency(char: Char) = filter{ it == char }.length

/**
 * @return the amount of characters differing between the two strings
 * @sample "abcdef" charDifference "abddef" = 1 (difference at position 2)
 * @sample "abcdef" charDifference "abcdefghij" = 4 (length difference of 4)
 * @sample "abcdef" charDifference "axxxefghij" = 7 (3 difference at position 1-3 + length difference of 4)
 */
infix fun String.charDifference(string: String): Int{
    var diff = abs(length - string.length)

    (0 until min(length, string.length)).forEach { i -> if (this[i] != string[i]) diff++ }
    return diff
}

/**
 * @return a newString without the differences between this and string params
 * @sample "abcdef" charDifference "abddef" = "abdef"
 * @sample "abcdef" charDifference "abcdefghij" = "abcdef"
 * @sample "abcdef" charDifference "axxxefghij" = "aef"
 */
infix fun String.removeDifferences(string: String): String{
    var newString = ""
    (0 until min(length, string.length)).forEach { i -> if (this[i] == string[i]) newString += this[i] }
    return newString
}