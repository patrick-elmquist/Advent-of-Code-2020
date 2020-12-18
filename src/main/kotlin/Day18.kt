import common.Day
import extension.asNumber

// Answer #1: 14006719520523
// Answer #2: 545115449981968

fun main() {
    Day(n = 18) {
        answer {
            lines.map { evaluate(it) }.sum()
        }

        answer {
            lines.map { evaluateAdvanced(it) }.sum()
        }
    }
}

private fun evaluateAdvanced(expression: String): Long {
    val list = expression.filter { it != ' ' }.toMutableList()
    var index = 0
    while (index < list.size) {
        if (list[index] != '+') {
            index++
            continue
        }

        var start = index - 1
        if (list[index - 1] == ')') {
            var nested = 0
            start -= list.subList(0, index).reversed().indexOfFirst {
                if (it == ')') nested++
                if (it == '(') nested--
                it == '(' && nested <= 0
            }.takeIf { it != -1 } ?: 0
        }
        list.add(start, '(')
        index++

        var end = index + 2
        if (list[index + 1] == '(') {
            var nested = 0
            end += list.subList(index + 1, list.size).indexOfFirst {
                if (it == ')') nested++
                if (it == '(') nested--
                it == ')' && nested >= 0
            }.takeIf { it != -1 } ?: list.size - 1 - end
        }

        list.add(end, ')')
        index++
    }
    return evaluate(list.joinToString(""))
}

private fun evaluate(expression: String): Long {
    var list = expression.filter { it != ' ' }
    var acc = 0L
    var expr = '+'
    while (list.isNotEmpty()) {
        val c = list.first()
        when {
            c == '+' || c == '*' -> expr = c
            c == '(' -> {
                var nested = 0
                val index = list.indexOfFirst {
                    if (it == '(') nested++
                    if (it == ')') nested--
                    nested == 0
                }
                val subString = list.substring(1, index)
                when (expr) {
                    '+' -> acc += evaluate(subString)
                    '*' -> acc *= evaluate(subString)
                }
                list = list.substring(index, list.length)
            }
            expr == '+' -> acc += c.asNumber()
            expr == '*' -> acc *= c.asNumber()
        }
        list = list.drop(1)
    }
    return acc
}
