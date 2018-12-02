/**
 * returns the frequency of a given Character in a String
 */
fun String.frequency(char: Char) = filter{ it == char }.length