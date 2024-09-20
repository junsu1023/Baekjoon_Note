private var n = 0
private lateinit var map: Array<IntArray>
private val direction = arrayOf(0 to -1, 1 to 0, 0 to 1, -1 to 0)
private val leftDirRate = arrayOf(
    intArrayOf(-2, 0, 2),
    intArrayOf(-1, -1, 10), intArrayOf(-1, 0, 7), intArrayOf(-1, 1, 1),
    intArrayOf(0, -2, 5), intArrayOf(0, -1, 100),
    intArrayOf(1, -1, 10), intArrayOf(1, 0, 7), intArrayOf(1, 1, 1),
    intArrayOf(2, 0, 2)
)

private val upDirRate = arrayOf(
    intArrayOf(-2, 0, 5),
    intArrayOf(-1, -1, 10), intArrayOf(-1, 0, 100), intArrayOf(-1, 1, 10),
    intArrayOf(0, -2, 2), intArrayOf(0, -1, 7), intArrayOf(0, 1, 7), intArrayOf(0, 2, 2),
    intArrayOf(1, -1, 1), intArrayOf(1, 1, 1)
)

private val rightDirRate = leftDirRate.changeRightRate()

private val downDirRate = upDirRate.changeDownRate()

private fun Array<IntArray>.changeRightRate(): Array<IntArray> {
    return this.map { intArrayOf(it[0], it[1] * -1, it[2]) }.toTypedArray()
}

private fun Array<IntArray>.changeDownRate(): Array<IntArray> {
    return this.map { intArrayOf(it[0] * -1, it[1], it[2]) }.toTypedArray()
}

private val rateByDirections = arrayOf(leftDirRate, downDirRate, rightDirRate, upDirRate)

private var outMapSand = 0

fun main() {
    input()
    process()
}

private fun input() {
    n = readln().toInt()
    map = Array(n) { IntArray(n) }

    repeat(n) {
        map[it] = readln().split(" ").map { it.toInt() }.toIntArray()
    }
}

private fun process() {
    var curTornadoY = n / 2
    var curTornadoX = n / 2

    var step = 1
    var curDir = 0

    while(step < n) {
        for(i in 0 until 2) {
            for(j in 0 until step) {
                val afterMoveIdx = moveTornado(curTornadoY, curTornadoX, curDir)
                curTornadoY = afterMoveIdx.first
                curTornadoX = afterMoveIdx.second
            }

            curDir = (curDir + 1) % 4
        }

        if(step == n - 1) {
            for(j in 0 until step) {
                val afterMoveIdx = moveTornado(curTornadoY, curTornadoX, curDir)
                curTornadoY = afterMoveIdx.first
                curTornadoX = afterMoveIdx.second
            }
        }
        step += 1
    }

    println(outMapSand)
}

private fun moveTornado(y: Int, x: Int, dir: Int): Pair<Int, Int> {
    val curDir = direction[dir]

    val willMoveY = y + curDir.first
    val willMoveX = x + curDir.second
    val mapRateState = rateByDirections[dir]

    moveSand(willMoveY, willMoveX, mapRateState)

    return Pair(willMoveY, willMoveX)
}

private fun moveSand(standardY: Int, standardX: Int, mapRateState: Array<IntArray>) {
    var maxRateIdx = Pair(0, 0)
    val totalSand = map[standardY][standardX]
    var remainSand = totalSand

    for((dirY, dirX, rate) in mapRateState) {
        val nextY = standardY + dirY
        val nextX = standardX + dirX

        if(rate == 100) {
            maxRateIdx = Pair(nextY, nextX)
            continue
        }

        val partialSand = (totalSand * (rate / 100.0)).toInt()
        remainSand -= partialSand

        if(nextY !in 0 until n || nextX !in 0 until n) {
            outMapSand += partialSand
        } else {
            map[nextY][nextX] += partialSand
        }
    }

    if(maxRateIdx.first !in 0 until n || maxRateIdx.second !in 0 until n) {
        outMapSand += remainSand
    } else {
        map[maxRateIdx.first][maxRateIdx.second] += remainSand
    }

    map[standardY][standardX] = 0
}