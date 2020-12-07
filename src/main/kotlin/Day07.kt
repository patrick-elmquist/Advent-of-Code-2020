import common.Day
import extension.*

// Answer #1: 151
// Answer #2: 41559

fun main() {
    Day(n = 7) {
        answer {
            calculateAncestors(lines.toReversedBagGraph(), "shiny gold").last()
        }
        answer {
            calculateBagCount(lines.toBagGraph(), "shiny gold")
        }
    }
}

private fun calculateAncestors(graph: Map<String, MutableSet<String>>, color: String): Sequence<Int> {
    val parentsToCheck = (graph[color] ?: error("")).toMutableList()
    return generateSequence(mutableSetOf<String>()) { set ->
        if (parentsToCheck.isEmpty()) return@generateSequence null
        val parentColor = parentsToCheck.removeFirst()
        parentsToCheck.addAll(graph[parentColor] ?: emptyList())
        set.apply { add(parentColor) }
    }.map { it.size }
}

private fun calculateBagCount(graph: Map<String, MutableMap<String, Int>>, color: String): Int {
    val bag = graph[color] ?: error("")
    if (bag.isEmpty()) return 0
    return bag.values.sum() + bag.map { (c, n) -> n * calculateBagCount(graph, c) }.sum()
}

private fun List<String>.toBagGraph() =
    filter { "no other" !in it }
        .fold(mutableMapOf<String, MutableMap<String, Int>>()) { bags, line ->
            bags.apply {
                val color = line.getColor()
                val bag = getOrPut(color) { mutableMapOf() }
                line.getBags().forEach { (otherColor, count) ->
                    getOrPut(otherColor) { mutableMapOf() }
                    bag[otherColor] = count
                }
            }
        }

private fun List<String>.toReversedBagGraph() =
    filter { "no other" !in it }
        .fold(mutableMapOf<String, MutableSet<String>>()) { bags, line ->
            bags.apply {
                val color = line.getColor()
                putIfAbsent(color, mutableSetOf())
                line.getBags().forEach { (otherColor, _) ->
                    getOrPut(otherColor, { mutableSetOf() }).also { bag -> bag.add(color) }
                }
            }
        }

private fun String.getColor() = split(WHITESPACE).take(2).joinToString(WHITESPACE)
private fun String.getBags(): Map<String, Int> {
    val i = indexOfFirst { it.isDigit() }
    val others = drop(i).split(", ").split(WHITESPACE)
    return others.map {
        it.drop(1).take(2).joinToString(WHITESPACE) to it.first().toInt()
    }.toMap()
}
