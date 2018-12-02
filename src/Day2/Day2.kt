package Day2

import Functions.Companion.readlines
import charDifference
import frequency
import removeDifferences

class Day2{
    companion object {
        fun part1(path: String): Int{
            var totalTwos = 0
            var totalThree = 0

            readlines("$path\\Day2\\input.txt").forEach { id ->
                var twos = false
                var threes = false
                id.forEach { c ->
                    if (!twos && id.frequency(c) == 2){
                        totalTwos++
                        twos = true
                    }

                    if (!threes && id.frequency(c) == 3){
                        totalThree++
                        threes = true
                    }
                }
            }

            return totalThree * totalTwos
        }

        fun part2(path: String): String{
            val list = readlines("$path\\Day2\\input.txt")

            (0 until list.size - 1).forEach { i ->
                (i + 1 until list.size).forEach { j ->
                    if (list[i] charDifference list[j] == 1) return list[i] removeDifferences list[j]
                }
            }

            return ""
        }
    }
}