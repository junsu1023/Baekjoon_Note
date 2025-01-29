import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max

lateinit var list: ArrayList<Int>
var n = 0
var k = 0

fun main() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine())
    n = st.nextToken().toInt()
    k = st.nextToken().toInt()
    list = ArrayList(100001)
    st = StringTokenizer(readLine())
    repeat(n) { list.add(st.nextToken().toInt()) }
    println(twoPointer())
}

fun twoPointer(): Int {
    var sum = 0
    var max = -99999
    var idx = 0
    for(i in 0 until n) {
        sum += list[i]
        idx++
        if(idx == k) {
            max = max(max, sum)
            sum -= list[i - k + 1]
            idx--
        }
    }
    return max
}