import java.text.DecimalFormat
import kotlin.math.abs

private var n = 0
private val points = mutableListOf<Pair<Double, Double>>()

fun main() {
    input()
    println(ccw())
}

private fun input() {
    n = readLine()!!.toInt()

    repeat(n) {
        val (y, x) = readLine()!!.split(" ").map { it.toDouble() }
        points.add(Pair(y, x))
    }
}

private fun ccw(): String {
    val p = points[0]

    var area = 0.0
    for(i in 0 until n - 2) {
        val p1 = points[i+1]
        val p2 = points[i+2]

        val diffY1 = p1.first - p.first
        val diffX1 = p1.second - p.second

        val diffY2 = p2.first - p1.first
        val diffX2 = p2.second - p1.second

        area += (diffY1 * diffX2 - diffX1 * diffY2) / 2
    }

    val df = DecimalFormat("#.0")

    return df.format(abs(area))
}