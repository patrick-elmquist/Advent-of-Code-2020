package common

data class Point(val x: Int, val y: Int) {
    operator fun plus(p2: Point) = Point(x + p2.x, y + p2.y)
}