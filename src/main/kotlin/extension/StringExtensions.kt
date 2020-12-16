package extension

fun String.joinToSingleLine(separator: String = EMPTY_STRING) = replace(NEW_LINE, separator)

