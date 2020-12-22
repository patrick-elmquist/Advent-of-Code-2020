import common.*
import extension.addVararg
import extension.splitOnBlank

// Answer #1: 33010
// Answer #2: 32769

fun main() {
    Day(n = 22) {
        answer {
            val (p1, p2) = lines.parseDecks()
            val winner = play(p1, p2).last()
            (1..winner.size)
                .zip(winner.reversed())
                .fold(0) { acc, (multiplier, card) -> acc + multiplier * card }
        }

        answer {
            val (p1, p2) = lines.parseDecks()
            val (_, winner) = playRec(p1.toMutableList(), p2.toMutableList(), 1)
            (1..winner.size)
                .zip(winner.reversed())
                .fold(0) { acc, (multiplier, card) -> acc + multiplier * card }
        }
    }
}

private fun playRec(p1: MutableList<Int>, p2: MutableList<Int>, game: Int): Pair<Boolean, List<Int>> {
    val seenDecks = mutableSetOf<Int>()
    while (p1.isNotEmpty() && p2.isNotEmpty()) {
        val p1h = p1.joinToString("", prefix = "p1").hashCode()
        val p2h = p2.joinToString("", prefix = "p2").hashCode()

        if (p1h in seenDecks && p2h in seenDecks) return true to p1

        seenDecks.add(p1h)
        seenDecks.add(p2h)

        val p1c = p1.removeFirst()
        val p2c = p2.removeFirst()
        if (p1.size >= p1c && p2.size >= p2c) {
            val (p1winner, _) = playRec(p1.take(p1c).toMutableList(), p2.take(p2c).toMutableList(), game + 1)
            addCardsToWinner(p1winner, p1, p1c, p2, p2c)
        } else {
            addCardsToWinner(p1c > p2c, p1, p1c, p2, p2c)
        }
    }
    return if (p2.isEmpty()) true to p1 else false to p2
}

private fun addCardsToWinner(p1Winner: Boolean, p1: MutableList<Int>, p1c: Int, p2: MutableList<Int>, p2c: Int) =
    if (p1Winner) p1.addVararg(p1c, p2c) else p2.addVararg(p2c, p1c)

private fun play(p1: List<Int>, p2: List<Int>) =
    generateSequence(p1.toMutableList() to p2.toMutableList()) { (p1, p2) ->
        when {
            p1.isEmpty() || p2.isEmpty() -> return@generateSequence null
            p1.first() > p2.first() -> p1.addVararg(p1.removeFirst(), p2.removeFirst())
            else -> p2.addVararg(p2.removeFirst(), p1.removeFirst())
        }
        p1 to p2
    }.map { (p1, p2) -> if (p1.size > p2.size) p1 else p2 }

private fun List<String>.parseDecks() =
    splitOnBlank().map { it.drop(1).map { n -> n.toInt() } }