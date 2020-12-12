import common.CardinalDirection
import common.Day
import common.Point
import kotlin.math.abs

// Answer #1: 562
// Answer #2: 101860

private const val LEFT = 'L'
private const val RIGHT = 'R'
private const val FORWARD = 'F'

fun main() {
    Day(n = 12) {
        answer {
            lines.parseInstructions()
                .fold(Point.ORIGO to CardinalDirection.EAST) { (ship, dir), (c, n) ->
                    when (c) {
                        LEFT -> ship to dir - n
                        RIGHT -> ship to dir + n
                        FORWARD -> ship.moveCardinalDirection(dir, n) to dir
                        else -> ship.moveCardinalDirection(c, n) to dir
                    }
                }.let { (ship, _) -> abs(ship.x) + abs(ship.y) }
        }
        answer {
            lines.parseInstructions()
                .fold(Point.ORIGO to Point(10, 1)) { (ship, waypoint), (c, n) ->
                    when (c) {
                        LEFT, RIGHT -> ship to waypoint.rotate(c, n)
                        FORWARD -> ship.moveForward(waypoint, n) to waypoint
                        else -> ship to waypoint.moveCardinalDirection(c, n)
                    }
                }.let { (ship, _) -> abs(ship.x) + abs(ship.y) }
        }
    }
}

private fun List<String>.parseInstructions() = map { it.first() to it.drop(1).toInt() }

private fun Point.moveForward(waypoint: Point, n: Int) =
    Point(x + waypoint.x * n, y + waypoint.y * n)

private fun Point.rotate(c: Char, n: Int) =
    when (if (c == LEFT) 360 - n else n) {
        0 -> this
        90 -> Point(y, x * -1)
        180 -> Point(x * -1, y * -1)
        270 -> Point(y * -1, x)
        else -> error("")
    }

private fun Point.moveCardinalDirection(c: Char, n: Int) =
    moveCardinalDirection(CardinalDirection.from(c), n)

private fun Point.moveCardinalDirection(dir: CardinalDirection, n: Int) =
    when (dir) {
        CardinalDirection.NORTH -> copy(y = y + n)
        CardinalDirection.SOUTH -> copy(y = y - n)
        CardinalDirection.EAST -> copy(x = x + n)
        CardinalDirection.WEST -> copy(x = x - n)
    }
