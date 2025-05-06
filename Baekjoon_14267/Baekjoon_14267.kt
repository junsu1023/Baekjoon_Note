private lateinit var firms: Array<MutableList<Int>>
private lateinit var scores: IntArray

fun main() {
    val (n, m) = readLine()!!.split(" ").map { it.toInt() }

    firms = Array(n + 1) { mutableListOf() }
    scores = IntArray(n + 1)

    val temp = readLine()!!.split(" ").map { it.toInt() }
    for(i in temp.indices) {
        val firm = if(temp[i] == -1) 0 else temp[i]
        firms[firm].add(i + 1)
    }

    repeat(m) {
        val (num, degree) = readLine()!!.split(" ").map { it.toInt() }
        scores[num] += degree
    }

    dfs(1)

    for(i in 1 .. n) {
        print("${scores[i]} ")
    }
}

private fun dfs(who: Int) {
    for(i in firms[who].indices) {
        val firm = firms[who][i]
        scores[firm] += scores[who]
        dfs(firm)
    }
}