package extension

import java.lang.IllegalArgumentException

fun <T : Collection<String>> T.toInts() = map { it.toInt() }
fun <T : Collection<String>> T.toLongs() = map { it.toLong() }
fun <T : Collection<String>> T.toFloats() = map { it.toFloat() }

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
        }.first.toList()

fun IntRange.permutations() = toList().permutations()
fun <T> List<T>.permutations(): Set<List<T>> = when {
    isEmpty() -> emptySet()
    size == 1 -> setOf(listOf(get(0)))
    else -> {
        val element = get(0)
        drop(1).permutations()
            .flatMap { sublist -> (0..sublist.size).map { i -> sublist.plusAt(i, element) } }
            .toSet()
    }
}

internal fun <T> List<T>.plusAt(index: Int, element: T): List<T> = when (index) {
    !in 0..size -> throw IllegalArgumentException("Index: $index Size: $size")
    0 -> listOf(element) + this
    size -> this + element
    else -> dropLast(size - index) + element + drop(index)
}
