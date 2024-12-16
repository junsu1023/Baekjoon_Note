import kotlin.math.abs

private val visitedList = mutableListOf<String>()
private val visited = Array(6) { BooleanArray(6) }

fun main() {
    input()
    solve()
}

private fun input() {
    repeat(36) {
        visitedList.add(readLine()!!)
    }
}

private fun solve() {
    val start = visitedList[0]
    var prev = start
    visited[start[0] - 'A'][start[1].digitToInt() - 1] = true

    var flag = true
    for(i in 1 until 36) {
        val cur = visitedList[i]

        if(visited[cur[0] - 'A'][cur[1].digitToInt() - 1] || !prev.canMove(cur)) {
            flag = false
            break
        }

        visited[cur[0] - 'A'][cur[1].digitToInt() - 1] = true
        prev = cur
    }

    if(flag) {
        if(!visitedList.last().canMove(start)) flag = false
    }

    println(if(flag) "Valid" else "Invalid")
}

private fun String.canMove(next: String): Boolean {
    val curSep = this.toCharArray()
    val nextSep = next.toCharArray()

    return if(abs((curSep[0]- 'A') - (nextSep[0] - 'A')) == 1 && abs(curSep[1].digitToInt() - nextSep[1].digitToInt()) == 2) true
    else if(abs((curSep[0]- 'A') - (nextSep[0] - 'A')) == 2 && abs(curSep[1].digitToInt() - nextSep[1].digitToInt()) == 1) true
    else false
}