package common

import extension.toFloats
import extension.toInts
import java.io.File

class Day(private val input: Input, block: Day.() -> Unit) {
    private var answerCount: Int = 1
        get() = field.also { field++ }

    constructor(n: Int, block: Day.() -> Unit) : this(Input(n), block)

    init {
        block(this)
    }

    fun answer(block: Input.() -> Any?) = answer(input, block)
    fun answer(vararg input: String, block: Input.() -> Any?) = answer(Input(*input), block)
    fun answer(input: List<String>, block: Input.() -> Any?) = answer(Input(input), block)

    private fun answer(input: Input, block: Input.() -> Any?) = println("Answer #${answerCount}: ${ block(input) }")
}

class Input(val lines: List<String>) {
    val floats by lazy { lines.toFloats() }
    val ints by lazy { lines.toInts() }
    val singleLine by lazy { lines.joinToString("") }
    val multiLine by lazy { lines.joinToString("\n") }

    constructor(day: Int) : this(File("./assets/input-day-$day.txt"))
    constructor(file: File) : this(file.readLines())
    constructor(vararg input: String) : this(input.asList())
}