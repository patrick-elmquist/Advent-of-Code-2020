import common.*

// Answer #1: 2230
// Answer #2: qqskn,ccvnlbp,tcm,jnqcd,qjqb,xjqd,xhzr,cjxv

@ExperimentalStdlibApi
fun main() {
    Day(n = 21) {
        answer {
            val data = lines.splitAllergensAndRecipes()
            val allergenIngredientMap = data.createAllergenRecipesMap()
                .filterCommonIngredients()
                .mapAllergenToIngredient()

            val allIngredients = data.flatMap { it.second }
            allIngredients.filter { it !in allergenIngredientMap.values }.count()
        }

        answer {
            val data = lines.splitAllergensAndRecipes()
            val allergenIngredientMap = data.createAllergenRecipesMap()
                .filterCommonIngredients()
                .mapAllergenToIngredient()
                .toList()

            allergenIngredientMap.sortedBy { it.first }.joinToString(",") { it.second }
        }
    }
}

private fun List<String>.splitAllergensAndRecipes() = map {
    val ingredients = it.substring(0, it.indexOf('(')).trim().split(' ')
    val allergens = it.substring(it.indexOf('('), it.length).drop(10).dropLast(1).split(", ")
    allergens to ingredients
}

private fun List<Pair<List<String>, List<String>>>.createAllergenRecipesMap() =
    flatMap { (allergens, recipe) -> allergens.map { it to recipe } }
        .groupBy({ it.first }, { it.second }).toMap()

private fun Map<String, List<List<String>>>.filterCommonIngredients() =
    map { (allergen, ingredients) ->
        allergen to ingredients.fold(ingredients.first().toSet()) { acc, set -> acc.intersect(set) }
    }.toMap()

@ExperimentalStdlibApi
private fun Map<String, Set<String>>.mapAllergenToIngredient() = buildMap<String, String> {
    val intersects = this@mapAllergenToIngredient.map { it.key to it.value.toMutableList() }.toMutableList()
    while (intersects.isNotEmpty()) {
        val item = intersects.first { it.second.size == 1 }
        intersects.remove(item)
        intersects.forEach { it.second.remove(item.second.first()) }
        put(item.first, item.second.first())
    }
}