import common.*

// Answer #1: 2275
// Answer #2: 2121

private const val EMPTY = 'L'
private const val OCCUPIED = '#'

fun main() {
    Day(n = 11) {
        answer {
            var seatMap = lines.toMatrix()
            do {
                val old = seatMap.hashCode()
                seatMap = seatMap.tick(4, maxDistance = 1)
            } while (seatMap.hashCode() != old)
            seatMap.flatten().count { seat -> seat == OCCUPIED }
        }
        answer {
            var seatMap = lines.toMatrix()
            do {
                val old = seatMap.hashCode()
                seatMap = seatMap.tick(5, maxDistance = Int.MAX_VALUE)
            } while (seatMap.hashCode() != old)
            seatMap.flatten().count { seat -> seat == OCCUPIED }
        }
    }
}

private fun Matrix<Char>.tick(leaveThreshold: Int, maxDistance: Int) =
    mapGrid { point, tile ->
        val count = countOccupiedNeighbours(point, maxDistance)
        when {
            tile == EMPTY && count == 0 -> OCCUPIED
            tile == OCCUPIED && count >= leaveThreshold -> EMPTY
            else -> tile
        }
    }

private fun Matrix<Char>.countOccupiedNeighbours(point: Point, distance: Int): Int {
    var count = 0
    count += checkDirection(point, NONE, UP, distance)
    count += checkDirection(point, NONE, DOWN, distance)
    count += checkDirection(point, LEFT, NONE, distance)
    count += checkDirection(point, RIGHT, NONE, distance)
    count += checkDirection(point, LEFT, UP, distance)
    count += checkDirection(point, RIGHT, UP, distance)
    count += checkDirection(point, LEFT, DOWN, distance)
    count += checkDirection(point, RIGHT, DOWN, distance)
    return count
}

private fun Matrix<Char>.checkDirection(point: Point, xDir: Int, yDir: Int, max: Int) =
    findInDirection(point, xDir, yDir, steps = max) { _, value ->
        when (value) {
            OCCUPIED -> true
            EMPTY -> false
            else -> null
        }
    }?.let { 1 } ?: 0
