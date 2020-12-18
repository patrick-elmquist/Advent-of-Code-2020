package common

fun List<String>.println() = this.onEach { println(it) }

fun List<CharSequence>.print() = this.apply { joinToString(" ") }

fun <T> T.print(prefix: String = "") = this.apply { println("$prefix $this") }