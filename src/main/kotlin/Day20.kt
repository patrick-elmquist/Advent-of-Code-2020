import common.Day
import extension.splitOnBlank

// Answer #1: 59187348943703
// Answer #2:

fun main() {
    Day(n = 20) {
        answer {
            lines.splitOnBlank()
                .toTileMap()
                .filterCornerTiles()
                .fold(1L) { acc, name -> acc * name.toLong() }
        }

        answer {

        }
    }
}

private fun Map<String, List<String>>.filterCornerTiles(): List<String> {
    val edgeCount = values.flatten().groupingBy { it }.eachCount()
    return map { (name, edges) -> name to edges.count { edgeCount[it] == 1 } }
        .filter { it.second == 4 }
        .map { it.first }
}

private fun List<List<String>>.toTileMap() = map { it.toTile() }.toMap()
private fun List<String>.toTile(): Pair<String, List<String>> {
    val sides = with(drop(1)) {
        mutableListOf(
            first(),
            map { it.last() }.joinToString(""),
            last().reversed(),
            map { it.first() }.joinToString("").reversed()
        )
    }
    return first().split(" ")[1].dropLast(1) to (sides + sides.map { it.reversed() })
}
