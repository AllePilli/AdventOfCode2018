package Day4

import Functions.Companion.readlines
import java.lang.IllegalArgumentException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Day4{/** https://adventofcode.com/2018/day/4 **/
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
    }
}

data class Event(val time: LocalDateTime, val guard: Int?, val inst: String)