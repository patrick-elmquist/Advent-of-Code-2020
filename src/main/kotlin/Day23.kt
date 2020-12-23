import common.*
import extension.asNumber
import java.util.*

// Answer #1: 38756249
// Answer #2: 21986479838

fun main() {
    Day(n = 23) {
        answer {
            val cups = lines.first().map { it.asNumber() }.toMutableList()
            val min = cups.minOf { it }
            val max = cups.maxOf { it }

            var n = 0
            var current = 0
            var offset = 0
            while (n < 100) {
                val c = cups[current]
                val pickUp = cups.next3(current).map { cups[it] }
                pickUp.forEach { cups.remove(it) }

                val destination = cups.findDestination(c, min, max)
                cups[destination]
                cups.addAll(destination + 1, pickUp)

                offset = current - cups.indexOf(c)
                Collections.rotate(cups, offset)
                current = (current + 1) % cups.size
                n++
            }
            Collections.rotate(cups, -cups.indexOf(1))
            cups.drop(1).joinToString("") == "38756249"
        }

        answer {
            val data = lines.first().map { it.asNumber().toLong() }.toMutableList()
            data.addAll((data.maxOf { it } + 1)..1000000)

            val (cups, root) = data.toLinkedList()
            var head = root
            for (n in 0 until 10000000) {
                val toMove = listOf(head.next, head.next.next, head.next.next.next)
                val next = toMove.last().next

                head.next = next
                cups.removeAll(toMove)

                val destination = cups.lower(head) ?: cups.last()
                toMove.last().next = destination.next
                destination.next = toMove.first()

                cups.addAll(toMove)
                head = next
            }
            val one = cups.floor(Node(1)) ?: error("")
            one.next.data * one.next.next.data
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

private fun List<Long>.toLinkedList(): Pair<TreeSet<Node>, Node> {
    val head = Node(get(0))
    var prev = head
    val cups = TreeSet<Node>()
    drop(1).forEach {
        val next = Node(it)
        cups.add(next)
        prev.next = next
        prev = next
    }
    prev.next = head
    return cups to head
}

private class Node(val data: Long) : Comparable<Node> {
    lateinit var next: Node
    override fun compareTo(other: Node) = data.compareTo(other.data)
}