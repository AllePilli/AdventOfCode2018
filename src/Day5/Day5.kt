package Day5

import Functions.Companion.readlines

class Day5{
    companion object {
        fun part1(path: String): Int{
            var polymer = readlines("$path\\Day5\\input.txt")[0]
            var prevPolymer = ""
            val regList = mutableListOf<Regex>()

            for (c in 'a'..'z'){
                regList.add(Regex("$c${c.toUpperCase()}"))
                regList.add(Regex("${c.toUpperCase()}$c"))
            }

            while (prevPolymer != polymer){
                prevPolymer = polymer
                regList.forEach { r ->
                    polymer = r.replaceFirst(polymer, "")
                }
            }

            return polymer.length
        }

        fun part2(path: String){

        }
    }
}