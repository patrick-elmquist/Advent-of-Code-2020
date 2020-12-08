import common.Day

// Answer #1: 2058
// Answer #2: 1000

fun main() {
    Day(n = 8) {
        answer {
            runBootCode(lines.toInstructions()).accumulator
        }
        answer {
            lines.toInstructions()
                .mapToSwappedLists()
                .map { runBootCode(it) }
                .first { it.terminated }
                .accumulator
        }
    }
}

private data class Instruction(val cmd: String, val offset: Int)
private data class Result(val accumulator: Int, val terminated: Boolean)

private fun List<String>.toInstructions() = map { it.toInstruction() }
private fun String.toInstruction() = Instruction(take(3), drop(4).toInt())

private fun runBootCode(instructions: List<Instruction>): Result {
    var accumulator = 0
    var pointer = 0
    val executed = mutableSetOf<Int>()

    while (pointer !in executed) {
        executed.add(pointer)
        val (cmd, offset) = instructions[pointer]
        pointer += when (cmd) {
            "acc" -> 1.also { accumulator += offset }
            "jmp" -> offset
            else -> 1
        }
        if (pointer !in instructions.indices) return Result(accumulator, true)
    }
    return Result(accumulator, false)
}

private fun List<Instruction>.mapToSwappedLists() =
    mapIndexedNotNull { index, instruction ->
        when (instruction.cmd) {
            "nop" -> toMutableList().also { it[index] = instruction.copy(cmd = "jmp") }
            "jmp" -> toMutableList().also { it[index] = instruction.copy(cmd = "nop") }
            else -> return@mapIndexedNotNull null
        }
    }