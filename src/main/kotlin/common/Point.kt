package common

data class Point(val x: Int = 0, val y: Int = 0) {
    operator fun plus(p2: Point) = Point(x + p2.x, y + p2.y)
    operator fun minus(p2: Point) = Point(x - p2.x, y - p2.y)
}
