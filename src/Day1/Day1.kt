package Day1

import Functions.Companion.readlines

class Day1{
    companion object {
        fun part1(path: String) = readlines("$path\\Day1\\input.txt").map { it.toInt() }.sum()
    }
}