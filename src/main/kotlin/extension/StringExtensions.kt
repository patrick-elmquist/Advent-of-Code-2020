package extension

fun <T: CharSequence> T.removeSpace() = filter { c -> c != ' ' }

