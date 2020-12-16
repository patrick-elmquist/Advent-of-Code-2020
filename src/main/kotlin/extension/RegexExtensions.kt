package extension

fun Regex.matchAndDestruct(input: String) =
    matchEntire(input)?.destructured ?: error("regex not valid for: $input")
