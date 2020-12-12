package common

data class Point(val x: Int, val y: Int) {
    operator fun plus(p2: Point) = Point(x + p2.x, y + p2.y)
    operator fun minus(p2: Point) = Point(x - p2.x, y - p2.y)

    companion object {
        val ORIGO: Point by lazy { Point(0, 0) }
    }
}
