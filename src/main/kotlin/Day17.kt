import common.Day
import common.Point4D
import common.mapGridNotNull
import common.toMatrix
import extension.*

// Answer #1: 362
// Answer #2: 1980

private val NEIGHBOUR_RANGE = -1..1
private val DEACTIVATE_RANGE = 2..3

fun main() {
    Day(n = 17) {
        answer {
            loop(n = 6, initial = lines.parseActive()) { it.cycle() }.size
        }

        answer {
            loop(n = 6, initial = lines.parseActive()) { it.cycle(include4D = true) }.size
        }
    }
}

private fun List<String>.parseActive() =
    toMatrix()
        .mapGridNotNull { point, value -> if (value == '#') Point4D(point) else null }
        .flatten()
        .toSet()

private fun Set<Point4D>.cycle(include4D: Boolean = false): Set<Point4D> {
    val nextGen = toMutableSet()
    contentRange { w }.forEach { w ->
        contentRange { z }.forEach { z ->
            contentRange { y }.forEach { y ->
                contentRange { x }.forEach { x ->
                    val cube = Point4D(x, y, z, w)
                    val count = countNeighbours(cube, include4D)
                    when {
                        cube !in this && count == 3 -> nextGen.add(cube)
                        count !in DEACTIVATE_RANGE -> nextGen.remove(cube)
                    }
                }
            }
        }
    }
    return nextGen
}

private fun Collection<Point4D>.contentRange(selector: Point4D.() -> Int) =
    (selector((min(selector))) - 1)..(selector(max(selector)) + 1)

private fun Set<Point4D>.countNeighbours(cube: Point4D, include4D: Boolean = false) =
    NEIGHBOUR_RANGE.sumBy { w ->
        NEIGHBOUR_RANGE.sumBy { z ->
            NEIGHBOUR_RANGE.sumBy { y ->
                NEIGHBOUR_RANGE.sumBy { x ->
                    val p = cube + Point4D(x, y, z, w)
                    if (p != cube && p in this && (include4D || w == 0)) 1 else 0
                }
            }
        }
    }
