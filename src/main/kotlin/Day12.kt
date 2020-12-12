import common.CardinalDirection
import common.CardinalDirection.*
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
                .fold(Point() to EAST) { (ship, dir), (c, n) ->
                    when (c) {
                        LEFT -> ship to dir.ccw(repeat = n / 90)
                        RIGHT -> ship to dir.cw(repeat = n / 90)
                        FORWARD -> ship.moveInDirection(dir, n) to dir
                        else -> ship.moveInDirection(c, n) to dir
                    }
                }.let { (ship, _) -> abs(ship.x) + abs(ship.y) }
        }
        answer {
            lines.parseInstructions()
                .fold(Point() to Point(10, 1)) { (ship, waypoint), (c, n) ->
                    when (c) {
                        LEFT, RIGHT -> ship to waypoint.rotate(c, n)
                        FORWARD -> ship.moveForward(waypoint, n) to waypoint
                        else -> ship to waypoint.moveInDirection(c, n)
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
        else -> error("what are you doing? >.>")
    }

private fun Point.moveInDirection(c: Char, n: Int) =
    moveInDirection(CardinalDirection.from(c), n)

private fun Point.moveInDirection(dir: CardinalDirection, n: Int) =
    when (dir) {
        NORTH -> copy(y = y + n)
        SOUTH -> copy(y = y - n)
        EAST -> copy(x = x + n)
        WEST -> copy(x = x - n)
    }
