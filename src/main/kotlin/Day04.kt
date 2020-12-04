import common.Day

// Answer #1: 213
// Answer #2: 147

private val REQUIRED = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

fun main() {
    Day(n = 4) {
        answer {
            lines.toPassports()
                .filterHasRequiredFields()
                .count()
        }
        answer {
            lines.toPassports()
                .filterHasRequiredFields()
                .filterHasValidFields()
                .count()
        }
    }
}

private fun List<String>.toPassports() =
    joinToString("\n").split("\n\n")
        .map { passport -> passport.split(" ", "\n").map { it.split(":") } }
        .map { passport -> passport.map { it[0] to it[1] }.toMap() }

private fun List<Map<String, String>>.filterHasRequiredFields() =
    filter { passport -> REQUIRED.count { it in passport.keys } == REQUIRED.size }

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
                "cid" -> true
                else -> false
            }
        }
    }
