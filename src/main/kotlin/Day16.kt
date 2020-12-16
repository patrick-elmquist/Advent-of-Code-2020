import common.Day
import extension.CSV
import extension.match
import extension.splitOnBlank
import extension.toInts

// Answer #1: 20231
// Answer #2: 1940065747861

private val CONDITION_REGEX = "(^[^:]*): ([0-9]{1,3})-([0-9]{1,3}) or ([0-9]{1,3})-([0-9]{1,3})".toRegex()

fun main() {
    Day(n = 16) {
        answer {
            val split = lines.splitOnBlank()
            val ranges = split[0].toConditions().values.flatten()

            split[2].drop(1)
                .toTickets()
                .map { it.filter { n -> ranges.all { range -> n !in range } }.sum() }
                .sum()
        }

        answer {
            val split = lines.splitOnBlank()
            val conditions = split[0].toConditions()
            val myTicket = split[1].drop(1).toTickets().first()
            val tickets = split[2].drop(1).toTickets()
            val ranges = conditions.values.flatten()
            val validTickets = filterValidTickets(tickets, ranges)

            findNameToColumnMap(validTickets, conditions)
                .getDepartureValuesForTicket(myTicket)
                .reduce { acc, i -> acc * i }
        }
    }
}

private fun filterValidTickets(tickets: List<List<Int>>, ranges: List<IntRange>) =
    tickets.filter { ticket -> ticket.all { number -> ranges.any { range -> number in range } } }

private fun List<String>.toConditions(): Map<String, List<IntRange>> =
    map { it.toCondition() }.toMap()

private fun Map<String, Int>.getDepartureValuesForTicket(myTicket: List<Int>) =
    filterKeys { it.contains("departure") }.values.map { myTicket[it].toLong() }

private fun List<String>.toTickets() = map { it.split(CSV).toInts() }
private fun String.toCondition() =
    CONDITION_REGEX
        .match(this) { (name, s1, e1, s2, e2) ->
            name to listOf(
                s1.toInt()..e1.toInt(),
                s2.toInt()..e2.toInt()
            )
        }

private fun findNameToColumnMap(tickets: List<List<Int>>, conditions: Map<String, List<IntRange>>): Map<String, Int> {
    val matchingConditions = tickets.first().indices
        .map { i -> i to findMatchingConditions(conditions, tickets, i) }
        .toMutableList()

    val columnToNameMap = mutableMapOf<String, Int>()
    while (matchingConditions.isNotEmpty()) {
        val entry = matchingConditions.minByOrNull { (_, conditions) -> conditions.size }
            ?: error("")

        val condition = entry.second.keys.first()
        matchingConditions.apply { remove(entry) }
            .forEach { (_, conditions) -> conditions.remove(condition) }

        columnToNameMap[condition] = entry.first
    }
    return columnToNameMap
}

private fun List<List<Int>>.getColumn(index: Int) = map { it[index] }
private fun findMatchingConditions(conditions: Map<String, List<IntRange>>, tickets: List<List<Int>>, index: Int) =
    conditions.filterValues { ranges ->
        tickets.getColumn(index).all { field -> ranges.any { range -> field in range } }
    }.toMutableMap()
