import common.*

// Answer #1: 495
// Answer #2: 4012

private val W = PointF(-2, 0)
private val E = PointF(2, 0)
private val NW = PointF(-1, 1)
private val NE = PointF(1, 1)
private val SW = PointF(-1, -1)
private val SE = PointF(1, -1)

fun main() {
    Day(n = 24) {
        answer {
            createMap().groupingBy { it }.eachCount().count { it.value % 2 == 1 }
        }

        answer {
            var blackTiles = createMap().groupingBy { it }.eachCount().filter { it.value % 2 == 1 }.keys
            repeat(100) {
                val whiteAdjacentTiles = mutableListOf<PointF>()
                val remainingBlackTiles = blackTiles.mapNotNull {
                    whiteAdjacentTiles.addAll(blackTiles.getAdjacentWhiteTiles(it))
                    if (blackTiles.countAdjacentBlackTiles(it) in 1..2) it else null
                }.toSet()

                val flippedWhiteTiles = whiteAdjacentTiles.mapNotNull {
                    if (blackTiles.countAdjacentBlackTiles(it) == 2) it else null
                }.toSet()

                blackTiles = remainingBlackTiles + flippedWhiteTiles
            }
            blackTiles.count()
        }
    }
}

private fun Set<PointF>.countAdjacentBlackTiles(p: PointF) =
    listOf(p + W, p + E, p + NW, p + NE, p + SW, p + SE).count { it in this }

private fun Set<PointF>.getAdjacentWhiteTiles(p: PointF) =
    listOf(p + W, p + E, p + NW, p + NE, p + SW, p + SE).filter { it !in this }

private fun Input.createMap() = lines.map {
    val line = it.toMutableList()
    var point = PointF()
    while (line.isNotEmpty()) {
        when (line.removeFirst()) {
            'e' -> point += PointF(2, 0)
            'w' -> point += PointF(-2, 0)
            'n' -> when (line.removeFirst()) {
                'e' -> point += PointF(1f, 1f)
                'w' -> point += PointF(-1f, 1f)
            }
            's' -> when (line.removeFirst()) {
                'e' -> point += PointF(1f, -1f)
                'w' -> point += PointF(-1f, -1f)
            }
        }
    }
    point
}
