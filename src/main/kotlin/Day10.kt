import common.Day
import kotlin.math.pow

// Answer #1: 2310
// Answer #2: 64793042714624

fun main() {
    Day(n = 10) {
        answer {
            ints.getSortedAdapters()
                .calculateDiffs()
                .filterIndexed { i, _ -> i == 0 || i == 2 }
                .reduce { a, b -> a * b }
        }
        answer {
            ints.getSortedAdapters()
                .createGroups()
                .calculatePermutations()
                .reduce { a, b -> a * b }
        }
    }
}

private fun List<Int>.calculateDiffs() =
    (1 until size)
        .fold(IntArray(3) to get(0)) { (diffs, last), i ->
            diffs.also { diffs[get(i) - last - 1]++ } to get(i)
        }.first

private fun List<Int>.getSortedAdapters() =
    (this + 0).sorted().let { it + (it.last() + 3) }

private fun List<Int>.createGroups(): List<List<Int>> {
    val list = mutableListOf<List<Int>>()
    var start = 0
    (1 until size).forEach { index ->
        if (get(index) - get(index - 1) > 1) {
            list.add(subList(start, index))
            start = index
        }
    }
    list.add(this.subList(start, size))
    return list
}

private fun List<List<Int>>.calculatePermutations() =
    map {
        when {
            it.size < 3 -> 1L
            it.size == 3 -> 2L
            it.size == 4 -> 4L
            it.size == 5 -> 7L
            else -> error("has the input changed?")
        }
    }
