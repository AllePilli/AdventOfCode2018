package Day1

import Functions.Companion.readlines

class Day1{ /** https://adventofcode.com/2018/day/1 **/
    companion object {
        fun part1(path: String) = readlines("$path\\Day1\\input.txt").map { it.toInt() }.sum()

        fun part2(path: String): Int{
            val input = readlines("$path\\Day1\\input.txt").map { it.toInt() }
            val list: MutableSet<Int> = mutableSetOf()
            var sum = 0

            while (true) input.forEach {
                sum += it
                if (!list.add(sum)) return sum
            }
        }
    }
}