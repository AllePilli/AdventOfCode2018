package Day3

data class Claim(val id: Int, val left: Int, val top: Int, val width: Int, val height: Int){
    companion object {
        private val reg = """^#(\d+) @ (\d+),(\d+): (\d+)x(\d+)$""".toRegex()
        fun parse(input: String): Claim = reg.find(input)?.let { result ->
            val (id, left, top, w, h) = result.destructured
            Claim(id.toInt(), left.toInt(), top.toInt(), w.toInt(), h.toInt())
        } ?: throw IllegalArgumentException("Can not parse $input")
    }

    fun area(): List<Pair<Int, Int>> = (left until left + width).flatMap { w ->
        (top until top + height).map { h ->
            Pair(w, h)
        }
    }
}