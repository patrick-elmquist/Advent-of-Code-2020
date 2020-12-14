import common.Day
import extension.set

// Answer #1: 11501064782628
// Answer #2: 5142195937660

fun main() {
    Day(n = 14) {
        answer {
            lines.map { line -> line.split(" = ") }
                .fold(mutableMapOf<Long, Long>() to "") { (memory, mask), split ->
                    if (isMask(split)) {
                        memory to getMask(split)
                    } else {
                        memory[getAddress(split)] = applyMaskToValue(mask, getValue(split))
                        memory to mask
                    }
                }.first.values.sum()
        }

        answer {
            lines.map { line -> line.split(" = ") }
                .fold(mutableMapOf<Long, Long>() to "") { (memory, mask), split ->
                    if (isMask(split)) {
                        memory to getMask(split)
                    } else {
                        calculateMaskedAddresses(mask, getAddress(split))
                            .forEach { address -> memory[address] = getValue(split) }
                        memory to mask
                    }
                }.first.values.sum()
        }
    }
}

private fun isMask(list: List<String>) = list.first() == "mask"
private fun getMask(list: List<String>) = list[1]
private fun getAddress(list: List<String>) = list.first().drop(4).dropLast(1).toLong()
private fun getValue(list: List<String>) = list[1].toLong()

private fun applyMaskToValue(mask: String, value: Long) =
    mask.reversed().foldIndexed(value) { index, acc, maskBit ->
        when (maskBit) {
            'X' -> acc
            '1' -> acc.set(index, 1)
            '0' -> acc.set(index, 0)
            else -> error("can't handle $maskBit")
        }
    }

private fun calculateMaskedAddresses(mask: String, address: Long) =
    applyMaskToAddressRecursive(mask.reversed(), address, 0)

private fun applyMaskToAddressRecursive(mask: String, address: Long, index: Int): Set<Long> =
    when {
        index == mask.length -> setOf(address)
        mask[index] == '0' -> applyMaskToAddressRecursive(mask, address, index + 1)
        mask[index] == '1' -> applyMaskToAddressRecursive(mask, address.set(index, 1), index + 1)
        mask[index] == 'X' -> {
            val one = applyMaskToAddressRecursive(mask, address.set(index, 1), index + 1)
            val zero = applyMaskToAddressRecursive(mask, address.set(index, 0), index + 1)
            one + zero
        }
        else -> error("can't handle ${mask[index]}")
    }
