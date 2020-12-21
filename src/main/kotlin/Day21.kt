import common.*

// Answer #1:
// Answer #2:

fun main() {
    val input = """
        mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
        trh fvjkl sbzzf mxmxvkd (contains dairy)
        sqjhc fvjkl (contains soy)
        sqjhc mxmxvkd sbzzf (contains fish)
    """.trimIndent().split("\n")
    Day(n = 21) {
        answer(input) {
            val map = mutableMapOf<String, MutableList<String>>()
            val list = lines.flatMap {
                val index = it.indexOf('(')
                val allergens = it.substring(index, it.length).drop(10).dropLast(1).split(", ")
                val ingredients = it.substring(0, index).trim().split(" ")

                allergens.map {
                    map.getOrPut(it, { mutableListOf()}).addAll(ingredients)
                    it to ingredients
                }
            }

            map.mapValues {
                it.value.groupingBy { it }.eachCount()
            }
        }

        answer {

        }
    }
}
