import common.Day
import extension.splitOnBlank

// Answer #1: 59187348943703
// Answer #2:

fun main() {
    Day(n = 20) {
        answer {
            val tiles = lines.splitOnBlank().map { it.toTile() }.toMap()
            val cornerTiles = tiles.findCornerTiles()
            cornerTiles.fold(1L) { acc, name -> acc * name.toLong() }
        }

        answer {

        }
    }
}

private fun Map<String, List<String>>.findCornerTiles(): List<String> {
    val edgeCount = values
        .flatten()
        .groupingBy { it }
        .eachCount()

    return map { (name, edges) -> name to edges.count { edgeCount[it] == 1 } }
        .filter { (_, count) -> count == 4 }
        .map { (name, _) -> name }
}

private fun List<String>.toTile(): Pair<String, List<String>> {
    val sides = with(drop(1)) {
        mutableListOf<String>(
            first(),
            map { it.last() }.joinToString(""),
            last().reversed(),
            map { it.first() }.joinToString("").reversed(),
            first().reversed(),
            map { it.last() }.joinToString("").reversed(),
            last(),
            map { it.first() }.joinToString("")
        )
    }
    return first().split(" ")[1].dropLast(1) to sides
}
