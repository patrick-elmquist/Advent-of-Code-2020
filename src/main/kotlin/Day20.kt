import common.*
import extension.splitOnBlank

// Answer #1: 59187348943703
// Answer #2: 1565

private val MONSTER = """
                      # 
    #    ##    ##    ###
     #  #  #  #  #  #   
""".trimIndent().split("\n")

fun main() {
    Day(n = 20) {
        answer {
            lines.splitOnBlank()
                .toTileMap()
                .filterCornerTiles()
                .fold(1L) { acc, name -> acc * name.toLong() }
        }

        answer {
            val monster = MONSTER
                .toMatrix()
                .mapGridNotNull { point, value -> if (value == '#') point else null }
                .flatten()

            lines.splitOnBlank().createTiles().createImage()
                .imageToTile()
                .orientations()
                .first { it.maskIfFound(monster) }
                .content
                .sumOf { row -> row.count { it == '#' } }
        }
    }
}

private fun List<List<Tile>>.imageToTile() =
    Tile(0,
        flatMap { row ->
            (1 until first().first().content.size - 1).map { y ->
                row.joinToString("") { it.insetRow(y) }.toMutableList()
            }
        }
    )

private fun List<Tile>.createImage(): List<List<Tile>> {
    val width = sqrt(size)
    var latestTile = findTopCorner()
    var latestHeader = latestTile
    return (0 until width).map { row ->
        (0 until width).map { col ->
            when {
                row == 0 && col == 0 -> latestTile
                col == 0 -> {
                    latestHeader = latestHeader.findAndOrientNeighbour(Direction.SOUTH, Direction.NORTH, this)
                    latestTile = latestHeader
                    latestHeader
                }
                else -> {
                    latestTile = latestTile.findAndOrientNeighbour(Direction.EAST, Direction.WEST, this)
                    latestTile
                }
            }
        }
    }
}

private fun List<List<String>>.createTiles() = map {
    Tile(
        it.first().split(" ")[1].dropLast(1).toLong(),
        it.drop(1).toMutableMatrix()
    )
}

private fun List<Tile>.findTopCorner() =
    first { tile -> tile.sharedSideCount(this) == 2 }
        .orientations()
        .first { it.isSideShared(Direction.SOUTH, this) && it.isSideShared(Direction.EAST, this) }


private data class Tile(val id: Long, var content: MutableMatrix<Char>) {
    private val sides = Direction.values().map { sideFacing(it) }.toSet()
    private val sidesReversed = sides.map { it.reversed() }.toSet()

    fun sharedSideCount(tiles: List<Tile>) =
        sides.sumOf { side -> tiles.filterNot { it.id == id }.count { tile -> tile.hasSide(side) } }

    fun isSideShared(dir: Direction, tiles: List<Tile>) =
        tiles.filterNot { it.id == id }
            .any { tile -> tile.hasSide(sideFacing(dir)) }

    fun findAndOrientNeighbour(mySide: Direction, theirSide: Direction, tiles: List<Tile>) =
        tiles
            .filterNot { it.id == id }
            .first { it.hasSide(sideFacing(mySide)) }
            .also { it.orientToSide(sideFacing(mySide), theirSide) }

    fun insetRow(row: Int) =
        content[row].drop(1).dropLast(1).joinToString("")

    fun maskIfFound(mask: List<Point>): Boolean {
        var found = false
        val maxWidth = mask.maxOfOrNull { it.y } ?: error("no maxWidth")
        val maxHeight = mask.maxOfOrNull { it.x } ?: error("no maxHeight")
        (0..(content.size - maxHeight)).forEach { x ->
            (0..(content.size - maxWidth)).forEach { y ->
                val lookingAt = Point(x, y)
                val actualSpots = mask.map { it + lookingAt }
                if (actualSpots.all { content[it.x][it.y] == '#' }) {
                    found = true
                    actualSpots.forEach { content[it.x][it.y] = '0' }
                }
            }
        }
        return found
    }

    fun orientations() = sequence {
        repeat(2) {
            repeat(4) {
                rotateClockwise()
                yield(this@Tile)
            }
            flip()
        }
    }

    private fun hasSide(side: String) = side in sides || side in sidesReversed

    private fun flip() {
        content = content.map { it.reversed().toMutableList() }
    }

    private fun rotateClockwise() {
        content = content.mapIndexed { x, row ->
            row.mapIndexed { y, _ ->
                content[y][x]
            }.reversed().toMutableList()
        }
    }

    private fun orientToSide(side: String, direction: Direction) =
        orientations().first { it.sideFacing(direction) == side }

    private fun sideFacing(dir: Direction) =
        when (dir) {
            Direction.NORTH -> content.first().joinToString("")
            Direction.SOUTH -> content.last().joinToString("")
            Direction.WEST -> content.map { it.first() }.joinToString("")
            Direction.EAST -> content.map { it.last() }.joinToString("")
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
