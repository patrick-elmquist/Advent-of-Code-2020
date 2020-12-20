package extension

const val WHITESPACE = " "
const val CSV = ","
const val COLON = ":"
const val QUOTE = "\""
const val PIPE = "|"

fun <T> loop(n: Int, initial: T, block: (T) -> T) =
    (0 until n).fold(initial) { acc, _ -> block(acc) }

