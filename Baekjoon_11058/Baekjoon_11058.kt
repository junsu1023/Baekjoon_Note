fun main() {
    val n = readLine()!!.toInt()
    dynamicProgramming(n)
}

private fun dynamicProgramming(n: Int) {
    val dp = LongArray(101)

    for(i in 1 .. n) {
        dp[i] = dp[i-1] + 1

        for(j in 3 until i) {
            dp[i] = maxOf(dp[i], dp[i-j] * (j - 1))
        }
    }

    println(dp[n])
}