import java.util.StringTokenizer

fun main() {
    val (n, k) = StringTokenizer(readLine()).run { IntArray(2) { nextToken().toInt() } }
    val arr = StringTokenizer(readLine()).run { IntArray(n) { nextToken().toInt() } }

    val prefixSum = IntArray(n + 1)
    prefixSum[1] = arr[0]
    for(i in 2 .. n) {
        prefixSum[i] = prefixSum[i-1] + arr[i - 1]
    }

    var maxValue = Long.MIN_VALUE
    for(i in k .. n) {
        val sum = prefixSum[i] - prefixSum[i - k]
        if(maxValue < sum) maxValue = sum.toLong()
    }

    println(maxValue)
}
