package common

import java.lang.IllegalArgumentException

enum class CardinalDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    fun rotate(cw: Boolean, repeat: Int = 1) =
        if(cw) cw(repeat) else ccw(repeat)

    fun cw(repeat: Int = 1) =
        (0 until repeat).fold(this) { current, _ ->
            when (current) {
                NORTH -> EAST
                EAST -> SOUTH
                SOUTH -> WEST
                WEST -> NORTH
            }
        }

    fun ccw(repeat: Int = 1) =
        (0 until repeat).fold(this) { current, _ ->
            when (current) {
                NORTH -> WEST
                WEST -> SOUTH
                SOUTH -> EAST
                EAST -> NORTH
            }
        }

    companion object {
        fun from(c: Char) = when (c) {
            'N' -> NORTH
            'S' -> SOUTH
            'E' -> EAST
            'W' -> WEST
            else -> throw IllegalArgumentException("Can't handle: $c")
        }
    }
}


