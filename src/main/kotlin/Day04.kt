import common.Day

// Answer #1: 213
// Answer #2: 147

private const val BLANK_LINE = "\n\n"
private val SPACE_OR_NEWLINE = arrayOf(" ", "\n")

fun main() {
    Day(n = 4) {
        answer {
            multiLine.toPassports()
                .filterHasRequiredFields()
                .count()
        }
        answer {
            multiLine.toPassports()
                .filterHasRequiredFields()
                .filterHasValidFields()
                .count()
        }
    }
}

private fun String.toPassports() =
    split(BLANK_LINE)
        .map { p -> p.split(*SPACE_OR_NEWLINE).map { it.split(":") } }
        .map { p -> p.associateBy({ it[0] }, { it[1] }) }

private fun List<Map<String, String>>.filterHasRequiredFields() =
    filter { p -> listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid").all { it in p.keys } }

private fun List<Map<String, String>>.filterHasValidFields() =
    filter { passport ->
        passport.all { (key, value) ->
            when (key) {
                "byr" -> value.toInt() in 1920..2002
                "iyr" -> value.toInt() in 2010..2020
                "eyr" -> value.toInt() in 2020..2030
                "hgt" -> when {
                    value.endsWith("cm") -> value.dropLast(2).toInt() in 150..193
                    value.endsWith("in") -> value.dropLast(2).toInt() in 59..76
                    else -> false
                }
                "hcl" -> value.startsWith("#") && value.drop(1).toIntOrNull(radix = 16) != null
                "ecl" -> value in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
                "pid" -> value.length == 9 && value.toIntOrNull() != null
                else -> "cid" == key
            }
        }
    }
