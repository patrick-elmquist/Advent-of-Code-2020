import common.Day

// Answer #1: 1038347917
// Answer #2: 137394018

fun main() {
    Day(n = 9) {
        answer {
            findWeakness(longs, n = 25)
        }
        answer {
            val weakness = findWeakness(longs, n = 25)
            longs.findContiguousSet(weakness).sumMinAndMax()
        }
    }
}

private fun List<Long>.findContiguousSet(weakness: Long) =
    mapIndexedNotNull { index, _ -> checkForContiguousSet(this, index, weakness) }
        .first()

private fun List<Long>.sumMinAndMax() = sorted().let { it.first() + it.last() }

private fun findWeakness(data: List<Long>, n: Int) =
    (n until data.size).first { i -> !containsSum(data[i], data.subList(i - n, i)) }
        .let { data[it] }

private fun containsSum(n: Long, preamble: List<Long>) =
    preamble.flatMap { a -> preamble.filter { b -> b != a }.map { b -> a + b == n } }
        .any { it }

private fun checkForContiguousSet(data: List<Long>, index: Int, weakness: Long): List<Long>? {
    val (end, sum) = generateSet(data, index, weakness).last()
    return (index..end).takeIf { sum == weakness }?.map { data[it] }
}

private fun generateSet(data: List<Long>, index: Int, number: Long) =
    generateSequence(index + 1 to data[index]) { (end, sum) ->
        when {
            end !in data.indices -> null
            sum + data[end] > number -> null
            else -> end + 1 to sum + data[end]
        }
    }
