import common.CardinalDirection
import common.CardinalDirection.*
import common.Day
import common.Point
import common.manhattanDistance
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
                .fold(Point() to EAST) { (ship, dir), (cmd, n) ->
                    when (cmd) {
                        LEFT, RIGHT -> ship to dir.rotate(cmd == RIGHT, repeat = n / 90)
                        FORWARD -> ship.moveInDirection(dir, n) to dir
                        else -> ship.moveInDirection(cmd, n) to dir
                    }
                }.let { (ship, _) -> ship.manhattanDistance() }
        }
        answer {
            lines.parseInstructions()
                .fold(Point() to Point(10, 1)) { (ship, waypoint), (cmd, n) ->
                    when (cmd) {
                        LEFT, RIGHT -> ship to waypoint.rotate(cmd, n)
                        FORWARD -> ship.moveForward(waypoint, n) to waypoint
                        else -> ship to waypoint.moveInDirection(cmd, n)
                    }
                }.let { (ship, _) -> ship.manhattanDistance() }
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
