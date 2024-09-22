private var n = 0
private var b = 0L
private lateinit var matrix: Array<IntArray>
private const val MOD = 1000

fun main() {
    solve()
}

private fun solve() {
    input()
    val result = process(b)
    output(result)
}

private fun input() {
    val input = readLine()!!.split(" ").map { it.toLong() }
    n = input[0].toInt(); b = input[1]

    matrix = Array(n) { IntArray(n) }
    repeat(n) {
        matrix[it] = readLine()!!.split(" ").map { it.toInt() % MOD }.toIntArray()
    }
}

private fun process(exponent: Long): Array<IntArray> {
    if(exponent == 1L) return matrix

    val temp = process(exponent / 2)
    var mul = multiply(temp, temp)

    if(exponent % 2 == 1L) {
       mul = multiply(mul, matrix)
    }

    return mul
}

private fun multiply(m1: Array<IntArray>, m2: Array<IntArray>): Array<IntArray> {
    val temp = Array(n) { IntArray(n) }

    for(i in 0 until n) {
        for(j in 0 until n) {
            for(k in 0 until n) {
                temp[i][j] += m1[i][k] * m2[k][j]
                temp[i][j] %= MOD
            }
        }
    }

    return temp
}

private fun output(arr: Array<IntArray>) {
    for(i in arr.indices) {
        for(j in arr[i].indices) {
            print("${arr[i][j]} ")
        }
        println()
    }
}