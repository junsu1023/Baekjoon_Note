private lateinit var a: String
private lateinit var b: String
private lateinit var c: String
private val dp = Array(101) { Array(101) { IntArray(101) } }

fun main() {
    input()
    println(lcs())
}

private fun input() {
    a = " ${readln()}"
    b = " ${readln()}"
    c = " ${readln()}"
}

private fun lcs(): Int {
    val aLen = a.length
    val bLen = b.length
    val cLen = c.length

    for(i in 1 until aLen) {
        for(j in 1 until bLen) {
            for(k in 1 until cLen) {
                if(a[i] == b[j] && a[i] == c[k]) dp[i][j][k] = dp[i-1][j-1][k-1] + 1
                else dp[i][j][k] = maxOf(dp[i-1][j][k], maxOf(dp[i][j-1][k], dp[i][j][k-1]))
            }
        }
    }

    return dp[aLen-1][bLen-1][cLen-1]
}