package Day3

import Functions.Companion.readlines

class Day3{
    companion object {
        fun part1(path: String): Int{
            val claims = readlines("$path\\Day3\\input.txt").map { Claim.parse(it) }
            return claims
                .flatMap { it.area() }
                .groupingBy { it }
                .eachCount()
                .count { it.value > 1 }
        }

        fun part2(path: String): Int{
            val claims = readlines("$path\\Day3\\input.txt").map { Claim.parse(it) }
            val cloth: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()
            val uncovered = claims.map { it.id }.toMutableSet()
            claims.forEach { claim ->
                claim.area().forEach { spot ->
                    val found = cloth.getOrPut(spot) {claim.id}
                    if (claim.id != found){
                        uncovered.remove(found)
                        uncovered.remove(claim.id)
                    }
                }
            }
            return uncovered.first()
        }
    }
}