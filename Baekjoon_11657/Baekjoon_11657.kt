import java.util.StringTokenizer

private var cityCnt = 0
private var busRoutCnt = 0
private lateinit var dist: LongArray
private val graph = mutableListOf<Triple<Int, Int, Int>>()

fun main() {
    input()
    solve()
}

private fun input() {
    var st = StringTokenizer(readLine())
    cityCnt = st.nextToken().toInt()
    busRoutCnt = st.nextToken().toInt()

    dist = LongArray(cityCnt + 1) { Long.MAX_VALUE }

    repeat(busRoutCnt) {
        st = StringTokenizer(readLine())
        val from = st.nextToken().toInt()
        val to = st.nextToken().toInt()
        val cost = st.nextToken().toInt()

        graph.add(Triple(from, to, cost))
    }
}

private fun solve() {
    dist[1] = 0
    for(i in 1 until cityCnt) {
        for(j in 0 until busRoutCnt) {
            val cur = graph[j]
            val from = cur.first
            val to = cur.second
            val cost = cur.third

            if(dist[from] == Long.MAX_VALUE) continue
            if(dist[to] > dist[from] + cost) dist[to] = dist[from] + cost
        }
    }

    for(i in 0 until busRoutCnt) {
        val cur = graph[i]
        val from = cur.first
        val to = cur.second
        val cost = cur.third

        if(dist[from] == Long.MAX_VALUE) continue
        if(dist[to] > dist[from] + cost) {
            println(-1)
            return
        }
    }

    for(i in 2 .. cityCnt) {
        if(dist[i] == Long.MAX_VALUE) println(-1)
        else println(dist[i])
    }
}