package Day2

import Functions.Companion.readlines
import frequency

class Day2{
    companion object {
        fun part1(path: String): Int{
            val list = readlines("$path\\Day2\\input.txt")
            var totalTwos = 0
            var totalThree = 0

            list.forEach { id ->
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
    }
}