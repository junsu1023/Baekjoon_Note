import java.util.LinkedList
import java.util.Queue

private var row = 0
private var col = 0
private lateinit var map: Array<CharArray>
private lateinit var robotIdx: Pair<Int, Int>
private lateinit var route: Array<IntArray>
private lateinit var dutyDistance: Array<IntArray>
private lateinit var visited: BooleanArray
private val dir = arrayOf(1 to 0, 0 to 1, -1 to 0, 0 to -1)
private var dutyCount = 2
private var ans = Int.MAX_VALUE

fun main() {
    while(true) {
        if(isEnd()) break
        calDutyDistance()

        if(cleanCheck()) {
            ans = Int.MAX_VALUE
            getShortestDistance(1, 1, 0)
            println(ans)
        } else {
            println(-1)
        }
    }
}

private fun isEnd(): Boolean {
    val (a, b) = readLine()!!.split(" ").map { it.toInt() }
    if(a == 0 && b == 0) return true

    row = b; col = a
    dutyCount = 2

    map = Array(row) { CharArray(col) }
    route = Array(row) { IntArray(col) }

    repeat(row) {
        map[it] = readLine()!!.toCharArray()

        for(i in 0 until col) {
            if(map[it][i] == 'o') {
                robotIdx = Pair(it, i)
                route[it][i] = 1
            }
            if(map[it][i] == '*') {
                route[it][i] = dutyCount++
            }
        }
    }

    return false
}

private fun calDutyDistance() {
    dutyDistance = Array(dutyCount) { IntArray(dutyCount) }
    visited = BooleanArray(dutyCount)

    for(i in 0 until row) {
        for(j in 0 until col) {
            if(route[i][j] != 0) {
                getDistance(i, j, route[i][j])
            }
        }
    }
}

private fun getDistance(y: Int, x: Int, idx: Int) {
    val que: Queue<Triple<Int, Int, Int>> = LinkedList()
    val visited = Array(row) { BooleanArray(col) }

    que.add(Triple(y, x, 0))
    visited[y][x] = true

    while(que.isNotEmpty()) {
        val curIdx = que.poll()
        val curY = curIdx.first
        val curX = curIdx.second
        val curCount = curIdx.third

        for(i in 0 until 4) {
            val nextY = curY + dir[i].first
            val nextX = curX + dir[i].second

            if(nextY !in 0 until row || nextX !in 0 until col) continue
            if(visited[nextY][nextX]) continue

            visited[nextY][nextX] = true
            if(map[nextY][nextX] != 'x') {
                if(route[nextY][nextX] != 0) {
                    dutyDistance[idx][route[nextY][nextX]] = curCount + 1
                }
                que.add(Triple(nextY, nextX, curCount + 1))
            }
        }
    }
}

private fun cleanCheck(): Boolean {
    for(i in 2 until dutyCount) {
        if(dutyDistance[1][i] == 0) return false
    }

    return true
}

private fun getShortestDistance(cur: Int, cnt: Int, distance: Int) {
    if(distance >= ans) return

    if(cnt == dutyCount - 1) {
        ans = minOf(ans, distance)
        return
    }

    for(i in 2 until dutyCount) {
        if(visited[i]) continue

        if(dutyDistance[cur][i] > 0) {
            visited[i] = true
            getShortestDistance(i, cnt + 1, distance + dutyDistance[cur][i])
            visited[i] = false
        }
    }
}