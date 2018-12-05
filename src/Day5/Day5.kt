package Day5

import Functions.Companion.readlines

class Day5{ /** https://adventofcode.com/2018/day/5 **/
    companion object {
        fun part1(path: String) = react(readlines("$path\\Day5\\input.txt")[0]).length

        fun part2(path: String): Int{
            val polymer = readlines("$path\\Day5\\input.txt")[0]
            val lenList = mutableListOf<Int>()
            for (c in 'a'..'z'){
                var poly = polymer
                val regLower = Regex("$c")
                val regUpper = Regex("${c.toUpperCase()}")

                poly = regLower.replace(poly, "")
                poly = regUpper.replace(poly, "")

                lenList.add(react(poly).length)
            }

            return lenList.min()!!
        }

        private fun react(polymer: String): String{
            var prevPolymer = ""
            var poly = polymer
            val regList = mutableListOf<Regex>()

            for (c in 'a'..'z'){
                regList.add(Regex("$c${c.toUpperCase()}"))
                regList.add(Regex("${c.toUpperCase()}$c"))
            }

            while (prevPolymer != poly){
                prevPolymer = poly
                regList.forEach { r -> poly = r.replaceFirst(poly, "") }
            }

            return poly
        }
    }
}