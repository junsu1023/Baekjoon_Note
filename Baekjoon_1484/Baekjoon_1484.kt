fun main() {
    solve(readLine()!!.toInt())
}

private fun solve(n: Int) {
    var i = 1

    val nums = mutableListOf<Int>()
    while(i * i < n) {
        if(n % i == 0) {
            val a = i
            val b = n / i

            if((a + b) % 2 == 0) {
                nums.add((a + b) / 2)
            }
        }
        i++
    }

    if(nums.isEmpty()) println(-1)
    else nums.sorted().forEach { println(it) }
}