import java.io.File

class Functions{
    companion object {
        fun readlines(path: String) = File(path).readLines()
    }
}