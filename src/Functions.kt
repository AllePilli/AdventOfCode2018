import java.io.File
import kotlin.math.abs

class Functions{
    companion object {
        fun readlines(path: String) = File(path).readLines()

        fun manhattanDist(p: Pair<Int, Int>, q: Pair<Int, Int>) = abs(p.first - q.first) + abs(p.second - q.second)
    }
}