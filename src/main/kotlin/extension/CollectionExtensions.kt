package extension

fun <T : Collection<String>> T.toInts() = map { it.toInt() }
fun <T : Collection<String>> T.toLongs() = map { it.toLong() }
fun <T : Collection<String>> T.toFloats() = map { it.toFloat() }

fun <E, T : Collection<E>> T.printAll(): T = onEach { println(it) }

fun <T : Collection<String>> T.mapSplit(separator: String = " ") =
    map { it.split(separator) }

fun <T> Collection<T>.max(selector: T.() -> Int) =
    maxByOrNull { selector(it) } ?: error("null when finding max")

fun <T> Collection<T>.min(selector: T.() -> Int) =
    minByOrNull { selector(it) } ?: error("null when finding min")

fun <E : CharSequence, T : List<E>> T.splitOnBlank() =
    (indices.filter { get(it).isEmpty() } + listOf(size))
        .fold(mutableListOf<List<E>>() to 0) { (list, start), end ->
            list.add(subList(start, end))
            list to end + 1
        }.first


