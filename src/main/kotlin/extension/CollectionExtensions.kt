package extension

fun <T: Collection<String>> T.toInts() = map { it.toInt() }
fun <T: Collection<String>> T.toLongs() = map { it.toLong() }
fun <T: Collection<String>> T.toFloats() = map { it.toFloat() }

fun <E,T : Collection<E>> T.printAll(): T = onEach { println(it) }

fun <T : Collection<String>> T.split(separator: String = " ") = map { it.split(separator) }
