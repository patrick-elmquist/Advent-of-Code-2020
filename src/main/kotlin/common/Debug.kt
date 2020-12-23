@file:Suppress("unused")

package common

private const val ENABLED = false
fun <T> T.print(prefix: String = "") = this.apply {
    if (ENABLED) println("$prefix $this")
}

fun <T> T.assert(to: T) = this.apply { check(this == to) { "Assert failed! Expected:$to Got:$this"} }
