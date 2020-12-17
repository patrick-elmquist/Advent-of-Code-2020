package common

import kotlin.math.abs

data class Point(val x: Int = 0, val y: Int = 0) {
    operator fun plus(p2: Point) = Point(x + p2.x, y + p2.y)
    operator fun minus(p2: Point) = Point(x - p2.x, y - p2.y)
}

fun Point.manhattanDistance(to: Point = Point(0, 0)) = abs(x - to.x) + abs(y - to.y)

data class Point4D(val x: Int = 0, val y: Int = 0, val z: Int = 0, val w: Int = 0) {
    constructor(point: Point): this(point.x, point.y)
    operator fun plus(p2: Point4D) = Point4D(x + p2.x, y + p2.y, z + p2.z, w + p2.w)
    operator fun minus(p2: Point4D) = Point4D(x - p2.x, y - p2.y, z - p2.z, w - p2.w)
}