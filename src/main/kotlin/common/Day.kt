package common

import extension.toInts
import extension.toLongs
import java.io.File
import kotlin.system.measureTimeMillis

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

    private fun answer(input: Input, block: Input.() -> Any?) {
        var result: Any?
        val elapsed = measureTimeMillis {
            result = block(input.copy())
        }
        println("Answer #${answerCount}: $result (${elapsed}ms)")
    }
}

data class Input(private var _lines: List<String>) {
    val lines: List<String> get() = test?.takeIf { !ignoreTest }?.split("\n") ?: _lines

    var test: String? = null
    var ignoreTest: Boolean = false

    val ints by lazy { lines.toInts() }
    val longs by lazy { lines.toLongs() }

    constructor(day: Int) : this(File("./assets/input-day-$day.txt"))
    constructor(file: File) : this(file.readLines())
    constructor(vararg input: String) : this(input.asList())
}