package Day4

import Functions.Companion.readlines
import java.lang.IllegalArgumentException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Day4{
    companion object {
        private val regex = "\\[([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})] (Guard #([0-9]+) begins shift|falls asleep|wakes up)".toRegex()

        fun part1(path: String): Int{
            val instructions = createInst("$path\\Day4\\input.txt")
            val map = createMap(instructions)

            val guard = map.maxBy { it.value.values.sum() }!!
            val minute = guard.value.entries.maxBy { it.value }!!.key

            return guard.key * minute
        }

        fun part2(path: String): Int{
            val instructions = createInst("$path\\Day4\\input.txt")
            val map = createMap(instructions)

            val max = map.map {
                it.key to it.value.maxBy { m -> m.value }!!
            }.maxBy { it.second.value } ?: throw IllegalArgumentException()

            return max.first * max.second.key
        }

        private fun createInst(path: String) = readlines(path).mapNotNull { regex.matchEntire(it)?.groupValues?.drop(1) }
            .map {
                val time = LocalDateTime.of(it[0].toInt(), it[1].toInt(), it[2].toInt(), it[3].toInt(), it[4].toInt())
                val guard = if (it[5].startsWith("Guard")) it[6].toInt() else null

                Event(time, guard, it[5])
            }
            .sortedBy { it.time }

        private fun createMap(instructions: List<Event>): MutableMap<Int, MutableMap<Int, Int>> {
            val map = mutableMapOf<Int, MutableMap<Int, Int>>()
            var guard = 0
            var asleep: LocalDateTime? = null

            instructions.forEach {
                when {
                    it.guard != null -> guard = it.guard
                    it.inst.startsWith("falls") -> asleep = it.time
                    it.inst.startsWith("wakes") -> {
                        val minutes = (asleep!!.minute until it.time.minute).map { m -> asleep!!.withMinute(m) }

                        minutes.forEach { m ->
                            map.computeIfAbsent(guard) { mutableMapOf() }.compute(m.minute) { _, v -> v?.plus(1) ?: 1 }
                        }
                    }
                }
            }

            return map
        }

        /*fun part1(path: String): Int{
            val (mapGuardAmtAsleep, mapGuardRanges) = getMaps(path)
            val guard = mapGuardAmtAsleep.maxBy { it.value }!!.key
            val times = mapGuardRanges[guard]!!

            val minimum = times.minBy { range -> range.first }!!.first
            var i = minimum
            val maximum = times.maxBy { range -> range.endInclusive }!!.endInclusive
            val rangeList: MutableList<Int> = mutableListOf()

            while (i <= maximum){
                rangeList.add(times.count { range -> range.contains(i) })
                i++
            }

            return guard * (rangeList.indexOf(rangeList.max()) + minimum)
        }

        fun part2(path: String): Int{
            val times = dateTimes(readlines("$path\\Day4\\input.txt"))
            val newTimes: MutableList<Pair<Int, String>> = mutableListOf()
            val schedule: MutableList<MutableList<String>> = mutableListOf()
            val regexGuardID = """#(\d+)""".toRegex()
            val regexAsleep = """asleep""".toRegex()
            var guardID = -1
            var oldGuardID = -1

            var j = 0

            (0 until times.size).forEach { i ->
                val entry = times[i]
                guardID = regexGuardID.find(entry.second)?.destructured?.component1()?.toInt() ?: guardID

                if (oldGuardID == guardID){

                }
            }

            return -1
        }

        private fun getMaps(path: String): Pair<HashMap<Int, Int>, HashMap<Int, MutableList<IntRange>>>{
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
                guardID = regexGuardID.find(entry.second)?.destructured?.component1()?.toInt() ?: guardID

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

            return Pair(mapGuardID, mapGuardRanges)
        }

        /*fun part2(path: String): Int{
            val (mapGuardAmtAsleep, mapGuardRanges) = getMaps(path)
            val (guardID, ranges) = mapGuardRanges.maxBy { entry ->
                val times = entry.value

                val minimum = times.minBy { range -> range.first }!!.first
                var i = minimum
                val maximum = times.maxBy { range -> range.endInclusive }!!.endInclusive
                val rangeList: MutableList<Int> = mutableListOf()


                while (i <= maximum){
                    rangeList.add(times.count { range -> range.contains(i) })
                    i++
                }

                rangeList.indexOf(rangeList.max()) + minimum
            }!!

            val times = mapGuardRanges[guardID]!!

            val minimum = ranges.minBy { range -> range.first }!!.first
            var i = minimum
            val maximum = ranges.maxBy { range -> range.endInclusive }!!.endInclusive
            val rangeList: MutableList<Int> = mutableListOf()

            while (i <= maximum){
                rangeList.add(ranges.count { range -> range.contains(i) })
                i++
            }

            println(guardID)
            println((rangeList.indexOf(rangeList.max()) + minimum))

            return guardID * (rangeList.indexOf(rangeList.max()) + minimum)
        }*/

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

        private val strToLocalDateTime: (String) -> LocalDateTime = {
            LocalDateTime.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        }*/
    }
}

data class Event(val time: LocalDateTime, val guard: Int?, val inst: String)