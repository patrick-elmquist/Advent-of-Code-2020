import common.Day
import extension.BLANK_LINE
import extension.NEW_LINE
import extension.joinToSingleLine

// Answer #1: 6249
// Answer #2: 3103

fun main() {
    Day(n = 6) {
        answer {
            lines.joinToString(NEW_LINE)
                .split(BLANK_LINE)
                .map { answers -> answers.joinToSingleLine() }
                .map { answers -> answers.toSet().count() }
                .sum()
        }
        answer {
            lines.joinToString(NEW_LINE)
                .split(BLANK_LINE)
                .map { answers -> answers.split(NEW_LINE).size to answers.joinToSingleLine() }
                .map { (size, answers) -> answers.groupingBy { it }.eachCount().count { (_, n1) -> n1 == size } }
                .sum()
        }
    }
}

