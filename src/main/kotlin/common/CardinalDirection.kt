package common

import java.lang.IllegalArgumentException

enum class CardinalDirection(private val degrees: Int) {
    NORTH(0),
    EAST(90),
    SOUTH(180),
    WEST(270);

    operator fun plus(degrees: Int): CardinalDirection {
        check(degrees % 90 == 0)
        val clean = degrees % 360
        return from((360 + this.degrees + clean) % 360)
    }

    operator fun minus(degrees: Int): CardinalDirection {
        check(degrees % 90 == 0)
        val clean = degrees % 360
        return from((360 + this.degrees - clean) % 360)
    }

    companion object {
        fun from(degrees: Int) = values().first { it.degrees == degrees }
        fun from(c: Char) = when (c) {
            'N' -> NORTH
            'S' -> SOUTH
            'E' -> EAST
            'W' -> WEST
            else -> throw IllegalArgumentException("Can't handle: $c")
        }
    }
}


