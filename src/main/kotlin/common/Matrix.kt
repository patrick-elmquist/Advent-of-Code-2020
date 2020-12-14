package common

const val NONE = 0
const val LEFT = -1
const val RIGHT = 1
const val UP = -1
const val DOWN = 1

typealias Matrix<T> = List<List<T>>
typealias MutableMatrix<T> = List<MutableList<T>>

fun <T : CharSequence> List<T>.toMatrix() = map { it.toList() }
operator fun <T> Matrix<T>.get(point: Point) = get(point.x, point.y)
operator fun <T> Matrix<T>.get(x: Int, y: Int) = this[y][x]
operator fun <T> Matrix<T>.contains(point: Point) = point.x in first().indices && point.y in indices

fun <T : CharSequence> List<T>.toMutableMatrix() = map { it.toMutableList() }
operator fun <T> MutableMatrix<T>.set(x: Int, y: Int, value: T) = value.also { this[y][x] = value }

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
        if (pos !in this) return null

        val decision = predicate(pos, this[pos])
        when {
            decision == true -> return this[pos]
            decision == false -> return null
            ++count == steps -> return null
        }
    }
}
