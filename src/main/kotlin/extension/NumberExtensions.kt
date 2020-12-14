package extension

fun Int.set(index: Int, bit: Int): Int {
    check(index >= 0 && index < Int.SIZE_BITS)
    return when (bit) {
        0 -> this and (1 shl index).inv()
        1 -> this or (1 shl index)
        else -> error("can only set values 0 and 1")
    }
}

fun Long.set(index: Int, bit: Int): Long {
    check(index >= 0 && index < Long.SIZE_BITS)
    return when (bit) {
        0 -> this and (1L shl index).inv()
        1 -> this or (1L shl index)
        else -> error("can only set values 0 and 1")
    }
}



