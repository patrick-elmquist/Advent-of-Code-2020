import common.*
import extension.asNumber
import java.util.*

// Answer #1:
// Answer #2:

fun main() {
    Day(n = 23) {
        answer {
            test = "389125467"
            ignoreTest = true

            val cups = lines.first().map { it.asNumber() }.toMutableList()
            val min = cups.minOf { it }
            val max = cups.maxOf { it }

            var n = 0
            var current = 0
            var offset = 0
            while (n < 100) {
                println("-- move ${n + 1} --")
                cups.mapIndexed { index, i->
                    if (index == current) "($i)"
                    else i.toString()
                }.joinToString(" ").print("cups:")

                val c = cups[current]
                val pickUp = cups.next3(current).map { cups[it] }
                pickUp.forEach { cups.remove(it) }
                pickUp.joinToString(" ").print("pick up:")

                val destination = cups.findDestination(c, min, max)
                cups[destination].print("destination:")
                cups.addAll(destination + 1, pickUp)

                cups.joinToString(" ").print()
                offset = current - cups.indexOf(c)
                offset.print("offset:")
                Collections.rotate(cups, offset)
                current = (current + 1) % cups.size
                n++

                println()
            }
            Collections.rotate(cups, -cups.indexOf(1))
            cups.drop(1).joinToString("")
        }

        answer {

        }
    }
}

private fun List<Int>.findDestination(current: Int, min: Int, max: Int): Int {
    var destination = current - 1
    while (destination !in this) {
        if (destination < min) {
            destination = max
        } else {
            destination--
        }
    }
    return indexOf(destination)
}

private fun List<Int>.next3(current: Int) =
    (1..3).map { (current + it) % this.size }
