package common

const val NONE = 0
const val LEFT = -1
const val RIGHT = 1
const val UP = -1
const val DOWN = 1

typealias Matrix<T> = List<List<T>>

fun <T: CharSequence> List<T>.toMatrix() = map { it.toList() }

fun <E, T> Matrix<T>.mapGrid(block: (point: Point, value: T) -> E): Matrix<E> =
    mapIndexed { y, list -> list.mapIndexed { x, value -> block(Point(x, y), value) } }

fun <T> Matrix<T>.findInDirection(
    point: Point,
    directionX: Int,
    directionY: Int,
    steps: Int = Int.MAX_VALUE,
    predicate: ((Point, T) -> Boolean?)
) = findInDirection(point.x, point.y, directionX, directionY, steps, predicate)

fun <T> Matrix<T>.findInDirection(
    originX: Int,
    originY: Int,
    directionX: Int,
    directionY: Int,
    steps: Int = Int.MAX_VALUE,
    predicate: ((Point, T) -> Boolean?)
): T? {
    val direction = Point(directionX, directionY)
    var count = 0
    var pos = Point(originX, originY)

    while (true) {
        pos += direction

        // verify that the point is within the matrix
        if (pos.x !in first().indices || pos.y !in indices) return null

        val decision = predicate(pos, this[pos.y][pos.x])
        when {
            decision == true -> return this[pos.y][pos.x]
            decision == false -> return null
            ++count == steps -> return null
        }
    }
}
