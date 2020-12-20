import common.Day
import extension.COLON
import extension.PIPE
import extension.QUOTE
import extension.WHITESPACE
import extension.splitOnBlank

// Answer #1: 195
// Answer #2: 309

fun main() {
    Day(n = 19) {
        answer {
            val (rulesInput, data) = lines.splitOnBlank()
            val regex = rulesInput.parse().patternFor(key = 0).toRegex()
            data.count(regex::matches)
        }

        answer {
            val (rulesInput, data) = lines.splitOnBlank()
            val rules = rulesInput.parse()
            val p31 = rules.patternFor(key = 31)
            val p42 = rules.patternFor(key = 42)
            val n = data.maxOfOrNull { it.length } ?: 0
            val regex = (1..(n - 1) / 2)
                .joinToString(PIPE) { i -> "(?:$p42){${i + 1},}(?:$p31){$i}" }
                .toRegex()
            data.count(regex::matches)
        }
    }
}

private fun List<String>.parse() = map { it.toRule() }.toMap()

private fun String.toRule(): Pair<Int, List<List<Rule>>> {
    val (lhs, rhs) = split(COLON)
    return lhs.toInt() to rhs.split(PIPE).map { branch ->
        branch.split(WHITESPACE).mapNotNull { item ->
            when {
                item.isEmpty() -> null
                item.contains(QUOTE) -> Rule.Letter(item.drop(1).dropLast(1))
                else -> Rule.Reference(item.toInt())
            }
        }
    }
}

private fun Map<Int, List<List<Rule>>>.patternFor(key: Int): String =
    (get(key) ?: error("no value for: $key"))
        .joinToString(separator = PIPE, prefix = "(?:", postfix = ")") { branch ->
            branch.joinToString("") { item ->
                when (item) {
                    is Rule.Letter -> Regex.escape(item.name)
                    is Rule.Reference -> patternFor(item.key)
                }
            }
        }

sealed class Rule {
    data class Letter(val name: String) : Rule()
    data class Reference(val key: Int) : Rule()
}
