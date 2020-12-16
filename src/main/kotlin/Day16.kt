import common.Day
import extension.CSV
import extension.matchAndDestruct
import extension.splitOnBlank
import extension.toInts

// Answer #1: 20231
// Answer #2: 1940065747861

private val CONDITION_REGEX = "(^[^:]*): ([0-9]{1,3})-([0-9]{1,3}) or ([0-9]{1,3})-([0-9]{1,3})".toRegex()

fun main() {
    Day(n = 16) {
        answer {
            val split = lines.splitOnBlank()
            val conditions = parseConditions(split[0])
            val tickets = split[2].drop(1).map { it.split(CSV).toInts() }
            val ranges = conditions.values.flatten()

            tickets.map { it.filter { n -> ranges.all { n !in it } }.sum() }.sum()
        }

        answer {
            val split = lines.splitOnBlank()
            val conditions = parseConditions(split[0])
            val myTicket = split[1].drop(1).first().split(CSV).toInts()
            val tickets = split[2].drop(1).map { it.split(CSV).toInts() }
            val ranges = conditions.values.flatten()

            solve2(filterValidTickets(tickets, ranges), myTicket, conditions)
        }
    }
}

private fun filterValidTickets(tickets: List<List<Int>>, ranges: List<IntRange>) =
    tickets.filter { ticket -> ticket.all { number -> ranges.any { range -> number in range } } }

private fun parseConditions(lines: List<String>): Map<String, List<IntRange>> =
    lines.map {
        CONDITION_REGEX.matchAndDestruct(it).let { (name, s1, e1, s2, e2) ->
            name to listOf(s1.toInt()..e1.toInt(), s2.toInt()..e2.toInt())
        }
    }.toMap()

private fun solve2(tickets: List<List<Int>>, myTicket: List<Int>, conditions: Map<String, List<IntRange>>): Long {
    val matchingConditions = tickets.first().indices.map { index ->
        index to findMatchingConditions(conditions, tickets.map { it[index] })
    }.toMutableList()

    val s = mutableMapOf<Int, String>()
    while (matchingConditions.isNotEmpty()) {
        val entry = matchingConditions.minByOrNull { (_, conditions) -> conditions.size } ?: error("")
        if (entry.second.isEmpty()) error("not possible")

        val condition = entry.second.keys.first()
        matchingConditions.remove(entry)
        matchingConditions.forEach {
            it.second.remove(condition)
        }
        s[entry.first] = condition
    }
    return s.filterValues { it.contains("departure") }
        .map { myTicket[it.key].toLong() }
        .reduce { acc, i -> acc * i }
}

private fun findMatchingConditions(
    conditions: Map<String, List<IntRange>>,
    column: List<Int>
) = conditions.filterValues { ranges ->
    column.all { field -> ranges.any { range -> field in range } }
}.toMutableMap()
