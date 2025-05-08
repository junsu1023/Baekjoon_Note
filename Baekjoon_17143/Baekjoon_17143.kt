private var row = 0
private var col = 0
private var sharkCnt = 0
private lateinit var map: Array<IntArray>
private val sharks = mutableMapOf<Int, Shark>()
private lateinit var afterMoveMap: Array<IntArray>

data class Shark(
    val y: Int, val x: Int, val speed: Int, val dir: Int, val size: Int
)

fun main() {
    input()
    process()
}

private fun input() {
    val base = readLine()!!.split(" ").map { it.toInt() }
    row = base[0]
    col = base[1]
    sharkCnt = base[2]

    map = Array(row + 1) { IntArray(col + 1) }

    repeat(sharkCnt) {
        val (y, x, speed, dir, size) = readLine()!!.split(" ").map { it.toInt() }
        sharks[it + 1] = Shark(y = y, x = x, speed = speed, dir = dir, size = size)
        map[y][x] = it + 1
    }
}

private fun process() {
    var kingIdx = 0
    var totalWeight = 0

    while(kingIdx < col) {
        kingIdx++
        totalWeight += catchShark(kingIdx)
        moveSharks()
        saveMap()
    }

    println(totalWeight)
}

private fun catchShark(kingIdx: Int): Int {
    for(i in 1 .. row) {
        if(map[i][kingIdx] == 0) continue

        val sharkNum = map[i][kingIdx]
        val shark = sharks[sharkNum]!!
        val size = shark.size

        sharks.remove(sharkNum)
        map[shark.y][shark.x] = 0

        return size
    }

    return 0
}

private fun moveSharks() {
    afterMoveMap = Array(row + 1) { IntArray(col + 1) }

    for(i in 1 .. row) {
        for(j in 1 .. col) {
            if(map[i][j] == 0) continue

            val num = map[i][j]
            val shark = sharks[num]!!
            move(num, shark)
        }
    }
}

private fun move(num: Int, shark: Shark) {
    when(shark.dir) {
        1 -> upDown(num, -1, shark)
        2 -> upDown(num, 1, shark)
        3 -> rightLeft(num, 1, shark)
        4 -> rightLeft(num, -1, shark)
    }
}

private fun upDown(num: Int, direction: Int, shark: Shark) {
    var curY = shark.y
    var dir = direction

    repeat(shark.speed) {
        if((curY == 1 && dir == -1) || (curY == row && dir == 1)) dir *= -1
        curY += dir
    }

    sharks[num] = shark.copy(y = curY, dir = if(dir == 1) 2 else 1)

    if(afterMoveMap[curY][shark.x] == 0) afterMoveMap[curY][shark.x] = num
    else {
        val occupyingSharkNum = afterMoveMap[curY][shark.x]
        eatUp(curY, shark.x, occupyingSharkNum, num)
    }
}

private fun rightLeft(num: Int, direction: Int, shark: Shark) {
    var curX = shark.x
    var dir = direction

    repeat(shark.speed) {
        if((curX == 1 && dir == -1) || (curX == col && dir == 1)) dir *= -1
        curX += dir
    }

    sharks[num] = shark.copy(x = curX, dir = if(dir == 1) 3 else 4)

    if(afterMoveMap[shark.y][curX] == 0) afterMoveMap[shark.y][curX] = num
    else {
        val occupyingSharkNum = afterMoveMap[shark.y][curX]
        eatUp(shark.y, curX, occupyingSharkNum, num)
    }
}

private fun eatUp(y: Int, x: Int, occupyingSharkNum: Int, curSharkNum: Int) {
    if(sharks[occupyingSharkNum]!!.size > sharks[curSharkNum]!!.size) {
        afterMoveMap[y][x] = occupyingSharkNum
        sharks.remove(curSharkNum)
    } else {
        afterMoveMap[y][x] = curSharkNum
        sharks.remove(occupyingSharkNum)
    }
}

private fun saveMap() {
    for(i in 1 .. row) {
        map[i] = afterMoveMap[i].clone()
    }
}