import java.util.StringTokenizer

private var n = 0
private var m = 0
private lateinit var scores: IntArray

fun main() {
    input()
    solve()
}

private fun input() {
    val nm = readLine()!!.split(" ").map { it.toInt() }
    n = nm[0]; m = nm[1]

    scores = StringTokenizer(readLine()).run { IntArray(n) { nextToken().toInt() } }
}

private fun solve() {
    println(binarySearch())
}

private fun binarySearch(): Int {
    var left = scores.min()
    var right = scores.sum()

    while(left <= right) {
        val mid = (left + right) / 2
        var sum = 0
        var group = 0

        for(i in 0 until n) {
            sum += scores[i]

            if(sum >= mid) {
                group++
                sum = 0
            }
        }

        if(group >= m) left = mid + 1
        else right = mid - 1
    }

    return right
}