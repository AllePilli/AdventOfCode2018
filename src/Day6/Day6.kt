package Day6

import Functions.Companion.manhattanDist
import Functions.Companion.readlines

class Day6{/** https://adventofcode.com/2018/day/6 **/
    companion object {
        fun part1(path: String): Int{
            val coordinates = readlines("$path\\Day6\\input.txt").map { l -> Pair(l.split(", ")[0].toInt(), l.split(", ")[1].toInt()) }
            val grid = mutableMapOf<Pair<Int, Int>, Int?>()
            val gridCopy = mutableMapOf<Pair<Int, Int>, Int?>()
            val areas = mutableMapOf<Int, Int>()
            val areasCopy = mutableMapOf<Int, Int>()
            var i = 0
            val limit = 10

            coordinates.forEach { co -> grid[co] = i++ }

            i = 0
            while (i <= limit){
                if (i == limit){
                    copy(gridCopy, grid)
                    iteration(gridCopy, coordinates)

                    calcAreas(areas, grid, coordinates)
                    calcAreas(areasCopy, gridCopy, coordinates)
                    return areas.filter { entry -> entry.value == areasCopy[entry.key] }.maxBy { entry -> entry.value }!!.value
                }else iteration(grid, coordinates)

                i++
            }

            return -1
        }

        fun part2(path: String){

        }

        private fun calcAreas(areas: MutableMap<Int, Int>, grid: MutableMap<Pair<Int, Int>, Int?>, coordinates: List<Pair<Int, Int>>){
            coordinates.forEach { co -> areas[grid[co]!!] = area(grid, grid[co]!!) }
        }

        private fun copy(gridCopy: MutableMap<Pair<Int, Int>, Int?>, grid: MutableMap<Pair<Int, Int>, Int?>){
            gridCopy.clear()
            grid.forEach { k, v -> gridCopy[k] = v }
        }

        private fun iteration(grid: MutableMap<Pair<Int, Int>, Int?>, coordinates: List<Pair<Int, Int>>){
            val min = Pair(grid.keys.minBy { it.first }!!.first - 1, grid.keys.minBy { it.second }!!.second - 1)
            val max = Pair(grid.keys.maxBy { it.first }!!.first + 1, grid.keys.maxBy { it.second }!!.second + 1)

            (min.first..max.first).forEach { x ->
                (min.second..max.second).forEach { y ->
                    if (!coordinates.contains(Pair(x, y))){
                        var value = coordinates.minBy { manhattanDist(it, Pair(x, y)) }!!//Pair(Int.MIN_VALUE, Int.MIN_VALUE)
                        var mindist = manhattanDist(value, Pair(x, y))
                        var equalDist = false

                        coordinates.forEach { co ->
                            if (co != value){
                                val dist = manhattanDist(co, Pair(x, y))

                                if (dist == mindist) equalDist = true
                                else if (dist < mindist){
                                    mindist = dist
                                    value = co
                                }
                            }
                        }

                        if (equalDist) grid[Pair(x, y)] = null
                        else grid[Pair(x, y)] = grid[value]
                    }
                }
            }
        }

        private fun area(grid: MutableMap<Pair<Int, Int>, Int?>, value: Int): Int{
            var area = 0
            grid.forEach { entry -> if (entry.value == value) area++ }
            return area
        }
    }
}