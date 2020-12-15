package common

import kotlin.math.abs

data class Point(val x: Int = 0, val y: Int = 0) {
    operator fun plus(p2: Point) = Point(x + p2.x, y + p2.y)
    operator fun minus(p2: Point) = Point(x - p2.x, y - p2.y)
}

fun Point.manhattanDistance(to: Point = Point(0, 0)) = abs(x - to.x) + abs(y - to.y)
