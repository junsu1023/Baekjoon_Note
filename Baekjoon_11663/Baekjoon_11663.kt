private val points = mutableListOf<Int>()
private val lines = mutableListOf<Pair<Int, Int>>()

fun main() {
    input()
    solve()
}

private fun input() {
    val (n, m) = readLine()!!.split(" ").map { it.toInt() }

    points.addAll(readLine()!!.split(" ").map { it.toInt() })
    points.sort()

    repeat(m) {
        val (a, b) = readLine()!!.split(" ").map { it.toInt() }
        lines.add(Pair(a, b))
    }
}

private fun solve() {
    for(line in lines) {
        println(upperBound(line.second) - lowerBound(line.first))
    }
}

private fun upperBound(target: Int): Int {
    var left = 0
    var right = points.size

    while(left < right) {
        val mid = (left + right) / 2

        if(points[mid] <= target) left = mid + 1
        else right = mid
    }

    return right
}

private fun lowerBound(target: Int): Int {
    var left = 0
    var right = points.size - 1

    while(left < right) {
        val mid = (left + right) / 2

        if(points[mid] < target) left = mid + 1
        else right = mid
    }

    if(points[right] < target) right++
    return right
}