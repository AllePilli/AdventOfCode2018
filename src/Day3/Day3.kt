package Day3

import Functions.Companion.readlines

class Day3{
    companion object {
        fun part1(path: String): Int{
            val lines = readlines("$path\\Day3\\input.txt")
            val grid = fillGrid(lines)
            var area = 0

            grid.forEach { row -> area += row.filter { it == 'x' }.size }

            return area
        }

        private fun fillGrid(lines: List<String>): MutableList<MutableList<Char>>{
            val grid: MutableList<MutableList<Char>> = mutableListOf()

            (0 until 2000).forEach { i ->
                grid.add(mutableListOf())
                (0 until 2000).forEach { j -> grid[i].add('.') }
            }

            lines.forEach { line ->
                val data = line.split(" @ ")[1]
                val co = data.split(": ")[0]
                val size = data.split(": ")[1]
                val x = co.split(",")[0].toInt()
                val y = co.split(",")[1].toInt()
                val width = size.split("x")[0].toInt()
                val height = size.split("x")[1].toInt()

                (x until x + width).forEach { i ->
                    (y until y + height).forEach { j ->
                        if (grid[i][j] == '.') grid[i][j] = '#'
                        else grid[i][j] = 'x'
                    }
                }
            }

            return grid
        }
    }
}