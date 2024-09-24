import java.util.LinkedList
import java.util.Queue

private var n = 0
private var m = 0
private var k = 0
private lateinit var map: Array<IntArray>
private lateinit var sharkDirections: IntArray
private lateinit var movePriority: Array<Array<IntArray>>
private val sharkIndex: Queue<SharkInfo> = LinkedList()
private lateinit var sharkSmell: Array<Array<Pair<Int, Int>>>
private val dir = arrayOf(0 to 0, -1 to 0, 1 to 0, 0 to -1, 0 to 1)
private lateinit var moveSharkIndex: MutableList<SharkInfo>

private data class SharkInfo(
    val num: Int,
    val dir: Int = -1,
    val y: Int,
    val x: Int
)


fun main() {
    input()
    println(solve())
}

private fun input() {
    val input = readLine()!!.split(" ").map { it.toInt() }
    n = input[0]; m = input[1]; k = input[2]

    map = Array(n) { IntArray(n) }
    sharkSmell = Array(n) { Array(n) { Pair(0, 0) } }
    moveSharkIndex = mutableListOf()

    repeat(n) {
        map[it] = readLine()!!.split(" ").map { it.toInt() }.toIntArray()
    }

    sharkDirections = IntArray(m)

    readLine()!!.split(" ").map { it.toInt() }.toIntArray().forEachIndexed { index, i ->
        sharkDirections[index] = i
    }

    movePriority = Array(m) { Array(4) { IntArray(4) } }

    repeat(m) { sharkNum ->
        repeat(4) { priority ->
            movePriority[sharkNum][priority] = readLine()!!.split(" ").map { it.toInt() }.toIntArray()
        }
    }
}

private fun solve(): Int {
    findShark()

    var time = 0
    while(true) {
        moveShark()
        spreadSmell()

        time++
        if(time > 1000) return -1

        if(sharkCount() == 1) break
    }

    return time
}

private fun findShark() {
    for(i in 0 until n) {
        for(j in 0 until n) {
            if(map[i][j] != 0) {
                sharkIndex.add(SharkInfo(map[i][j], sharkDirections[map[i][j] - 1], i, j))
                sharkSmell[i][j] = Pair(map[i][j], k)
            }
        }
    }
}

private fun moveShark() {
    while(sharkIndex.isNotEmpty()) {
        val curShark = sharkIndex.poll()
        val sharkCurNum = curShark.num
        val sharkCurDir = curShark.dir
        val sharkCurY = curShark.y
        val sharkCurX = curShark.x

        val (changeDir, sharkNextY, sharkNextX) = priorityForMove(sharkCurNum, sharkCurDir, sharkCurY, sharkCurX)

        // 작은 번호가 이김
        if(map[sharkNextY][sharkNextX] != 0) {
            if(map[sharkNextY][sharkNextX] > sharkCurNum) {
                val filter = moveSharkIndex.filter { it.num != map[sharkNextY][sharkNextX] }
                moveSharkIndex = mutableListOf()
                moveSharkIndex.addAll(filter)

                map[sharkNextY][sharkNextX] = sharkCurNum
                moveSharkIndex.add(SharkInfo(map[sharkNextY][sharkNextX], changeDir, sharkNextY, sharkNextX))
            }
        }
        else {
            map[sharkNextY][sharkNextX] = sharkCurNum
            moveSharkIndex.add(SharkInfo(map[sharkNextY][sharkNextX], changeDir, sharkNextY, sharkNextX))
        }

        map[sharkCurY][sharkCurX] = 0
    }

    for(sharkInfo in moveSharkIndex) {
        sharkIndex.add(sharkInfo)
    }

    moveSharkIndex = mutableListOf()
}

private fun priorityForMove(sharkNum: Int, direction: Int, y: Int, x: Int): Triple<Int, Int, Int> {
    val priorityList = movePriority[sharkNum-1][direction-1]

    for(i in priorityList.indices) {
        val d = priorityList[i]
        val nextY = y + dir[d].first
        val nextX = x + dir[d].second

        if(nextY !in 0 until n || nextX !in 0 until n) continue

        if(sharkSmell[nextY][nextX].first == 0 && sharkSmell[nextY][nextX].second == 0) return Triple(d, nextY, nextX)
    }


    for(i in priorityList.indices) {
        val d = priorityList[i]
        val nextY = y + dir[d].first
        val nextX = x + dir[d].second

        if(nextY !in 0 until n || nextX !in 0 until n) continue

        if(sharkSmell[nextY][nextX].first == sharkNum) return Triple(d, nextY, nextX)
    }

    return Triple(-1, -1, -1)
}

private fun spreadSmell() {
    for(i in 0 until n) {
        for(j in 0 until n) {
            if(sharkSmell[i][j].first != 0) {
                val sharkNum = sharkSmell[i][j].first
                val smellCount = sharkSmell[i][j].second - 1

                sharkSmell[i][j] = if(smellCount == 0) Pair(0, 0) else Pair(sharkNum, smellCount)
            }

            if(map[i][j] != 0) sharkSmell[i][j] = Pair(map[i][j], k)
        }
    }
}

private fun sharkCount(): Int {
    var sharkCnt = 0
    for(i in 0 until n) {
        for(j in 0 until n) {
            if(map[i][j] != 0) sharkCnt++
        }
    }

    return sharkCnt
}