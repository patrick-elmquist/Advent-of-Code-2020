import common.Day
import extension.Matrix
import extension.toMatrix

// Answer #1: 2275
// Answer #2: 2121

private const val EMPTY = 'L'
private const val OCCUPIED = '#'

private const val NONE = 0
private const val LEFT = -1
private const val RIGHT = 1
private const val UP = -1
private const val DOWN = 1

fun main() {
    Day(n = 11) {
        answer {
            var seatMap = lines.toMatrix()
            do {
                val old = seatMap.hashCode()
                seatMap = seatMap.tick(4, distance = 1)
            } while (seatMap.hashCode() != old)
            seatMap.joinToString("").count { c -> c == OCCUPIED }
        }
        answer {
            var seatMap = lines.toMatrix()
            do {
                val old = seatMap.hashCode()
                seatMap = seatMap.tick(5, distance = Int.MAX_VALUE)
            } while (seatMap.hashCode() != old)
            seatMap.joinToString("").count { c -> c == OCCUPIED }
        }
    }
}

private fun Matrix<Char>.tick(leaveThreshold: Int, distance: Int) =
    mapIndexed { row, r ->
        r.mapIndexed { col, c ->
            val count = countOccupiedNeighbours(col, row, distance = distance)
            when {
                c == EMPTY && count == 0 -> OCCUPIED
                c == OCCUPIED && count >= leaveThreshold -> EMPTY
                else -> c
            }
        }
    }

private fun Matrix<Char>.countOccupiedNeighbours(col: Int, row: Int, distance: Int): Int {
    var count = 0
    count += checkDirection(col, row, NONE, UP, distance)
    count += checkDirection(col, row, NONE, DOWN, distance)
    count += checkDirection(col, row, LEFT, NONE, distance)
    count += checkDirection(col, row, RIGHT, NONE, distance)
    count += checkDirection(col, row, LEFT, UP, distance)
    count += checkDirection(col, row, RIGHT, UP, distance)
    count += checkDirection(col, row, LEFT, DOWN, distance)
    count += checkDirection(col, row, RIGHT, DOWN, distance)
    return count
}

private fun Matrix<Char>.checkDirection(col: Int, row: Int, cDir: Int, rDir: Int, max: Int): Int {
    val cols = first().size
    val rows = size
    var c = col
    var r = row
    var count = 0

    while (true) {
        c += cDir
        r += rDir
        when {
            c < 0 || c >= cols || r < 0 || r >= rows -> return 0
            this[r][c] == OCCUPIED -> return 1
            this[r][c] == EMPTY -> return 0
            ++count == max -> return 0
        }
    }
}
