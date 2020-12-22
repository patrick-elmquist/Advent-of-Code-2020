@file:Suppress("unused")

package common

fun <T> T.print(prefix: String = "") = this.apply { println("$prefix $this") }

fun <T> T.assert(to: T) = this.apply { check(this == to) { "Assert failed! Expected:$to Got:$this"} }
