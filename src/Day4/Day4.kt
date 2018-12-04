package Day4

import Functions.Companion.readlines
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Day4{
    companion object {
        fun part1(path: String): Int{
            val list = dateTimes(readlines("$path\\Day4\\input.txt"))
            val mapGuardID: HashMap<Int, Int> = hashMapOf()
            val mapGuardRanges: HashMap<Int, MutableList<IntRange>> = hashMapOf()
            val regexGuardID = """#(\d+)""".toRegex()
            val regexAsleep = """asleep""".toRegex()
            var guardID = -1
            var oldGuardID = -1
            var i = 0

            var startSleep: LocalDateTime = LocalDateTime.now()
            var stopSleep: LocalDateTime

            while (i < list.size){
                val entry = list[i]
                guardID = regexGuardID.find(entry.second)?.let {result ->
                    result.destructured.component1().toInt()
                } ?: guardID

                //if (i == 0) oldGuardID = guardID

                if (oldGuardID == guardID){
                    val fallsAsleep = regexAsleep.find(entry.second)?.let { true } ?: false
                    if (fallsAsleep) startSleep = entry.first
                    else {
                        stopSleep = entry.first
                        if (mapGuardID.containsKey(guardID)) mapGuardID[guardID] = mapGuardID[guardID]!! + (stopSleep.minute - startSleep.minute)
                        else mapGuardID.put(guardID, stopSleep.minute - startSleep.minute)

                        if (mapGuardRanges.containsKey(guardID)) mapGuardRanges[guardID]!!.add(IntRange(startSleep.minute, stopSleep.minute - 1))
                        else mapGuardRanges.put(guardID, mutableListOf(IntRange(startSleep.minute, stopSleep.minute - 1)))
                    }
                }

                oldGuardID = guardID
                i++
            }

            val guard = mapGuardID.maxBy { it.value }!!.key
            val times = mapGuardRanges[guard]!!

            val minimum = times.minBy { range -> range.first }!!.first
            i = minimum
            val maximum = times.maxBy { range -> range.endInclusive }!!.endInclusive
            val rangeList: MutableList<Int> = mutableListOf()

            while (i <= maximum){
                rangeList.add(times.count { range -> range.contains(i) })
                i++
            }

            return guard * (rangeList.indexOf(rangeList.max()) + minimum)
        }

        fun part2(path: String){

        }

        private fun dateTimes(strings: List<String>): List<Pair<LocalDateTime, String>>{
            val list: MutableList<Pair<LocalDateTime, String>> = mutableListOf()
            val regex = """\d+-\d+-\d+ \d+:\d+""".toRegex()

            strings.forEach { str ->
                val match = regex.find(str)!!.value
                list.add(Pair(strToLocalDateTime(match), str.split("$match] ")[1]))
            }

            list.sortBy { it.first }
            return list
        }

        val strToLocalDateTime: (String) -> LocalDateTime = {
            LocalDateTime.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        }
    }
}