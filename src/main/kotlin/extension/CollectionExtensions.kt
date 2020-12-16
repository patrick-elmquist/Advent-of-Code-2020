package extension

fun <T: Collection<String>> T.toInts() = map { it.toInt() }
fun <T: Collection<String>> T.toLongs() = map { it.toLong() }
fun <T: Collection<String>> T.toFloats() = map { it.toFloat() }

fun <E,T : Collection<E>> T.printAll(): T = onEach { println(it) }

fun <T : Collection<String>> T.split(separator: String = " ") = map { it.split(separator) }

fun <E: CharSequence, T: List<E>> T.splitOnBlank(): List<List<E>> {
    val blanks = this.mapIndexedNotNull { index, n ->
        if (n.isEmpty()) index else null
    } + listOf(size)

    return blanks.fold(mutableListOf<List<E>>() to 0) { (list, start), end ->
        list.also { it.add(this.subList(start, end)) } to end + 1
    }.first
}
