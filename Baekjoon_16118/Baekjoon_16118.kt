import java.io.*
import java.util.*


private var nodes = 0
private var edges = 0
private lateinit var graph: Array<MutableList<Node>>

data class Node(val idx: Int, val state: Int? = null, val distance: Long)

fun main() {
    input()
    solve()
}

private fun input() {
    val br = InputReader(System.`in`)
    nodes = br.readInt()
    edges = br.readInt()

    graph = Array(nodes + 1) { mutableListOf() }

    repeat(edges) {
        val from = br.readInt()
        val to = br.readInt()
        val len = br.readInt() * 2L

        graph[from].add(Node(idx = to, distance = len))
        graph[to].add(Node(idx = from, distance = len))
    }
}

private fun solve() {
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    val foxDistResult = foxDijkstra()
    val wolfDistResult = wolfDijkstra()

    var cnt = 0
    for(i in 1 .. nodes) {
        if(foxDistResult[i] < wolfDistResult[i]) cnt++
    }

    bw.write("$cnt\n")
    bw.close()
}

private fun foxDijkstra(): LongArray {
    val pq = PriorityQueue<Node>(compareBy { it.distance })
    val minimumDistance = LongArray(nodes + 1) { 987654321 }

    pq.add(Node(idx = 1, distance = 0))
    minimumDistance[1] = 0

    while(pq.isNotEmpty()) {
        val cur = pq.poll()
        val curIdx = cur.idx
        val curDistance = cur.distance

        if(curDistance > minimumDistance[curIdx]) continue

        for(next in graph[curIdx]) {
            val nextIdx = next.idx
            val totalDistance = curDistance + next.distance

            if(totalDistance >= minimumDistance[nextIdx])  continue

            pq.add(Node(idx = nextIdx, distance = totalDistance))
            minimumDistance[nextIdx] = totalDistance
        }
    }

    return minimumDistance
}

private fun wolfDijkstra(): LongArray {
    val pq = PriorityQueue<Node>(compareBy { it.distance })
    val distance = Array(nodes + 1) { LongArray(2) { 987654321 } }

    pq.add(Node(idx = 1, state = 0, distance = 0))
    distance[1][0] = 0

    while(pq.isNotEmpty()) {
        val cur = pq.poll()
        val curIdx = cur.idx
        val curState = cur.state
        val curDistance = cur.distance

        if(curDistance > distance[curIdx][curState!!]) continue

        for(next in graph[curIdx]) {
            val nextIdx = next.idx
            val nextState = 1 - curState
            val totalDistance = if(nextState == 1) curDistance + next.distance / 2  else curDistance + next.distance * 2

            if(totalDistance >= distance[nextIdx][nextState]) continue

            pq.add(Node(idx = nextIdx, state = nextState, distance = totalDistance))
            distance[nextIdx][nextState] = totalDistance
        }
    }

    val minimumDistance = LongArray(nodes + 1)
    for(i in 1 .. nodes) {
        minimumDistance[i] = minOf(distance[i][0], distance[i][1])
    }

    return minimumDistance
}

private class InputReader(private val stream: InputStream) {
    private val buf = ByteArray(1024)
    private var curChar = 0
    private var numChars = 0
    private val filter: SpaceCharFilter? = null

    fun read(): Int {
        if (numChars == -1) {
            throw InputMismatchException()
        }
        if (curChar >= numChars) {
            curChar = 0
            try {
                numChars = stream.read(buf)
            } catch (e: IOException) {
                throw InputMismatchException()
            }
            if (numChars <= 0) {
                return -1
            }
        }
        return buf[curChar++].toInt()
    }

    fun readInt(): Int {
        var c = read()
        while (isSpaceChar(c)) {
            c = read()
        }
        var sgn = 1
        if (c == '-'.code) {
            sgn = -1
            c = read()
        }
        var res = 0
        do {
            if (c < '0'.code || c > '9'.code) {
                throw InputMismatchException()
            }
            res *= 10
            res += c - '0'.code
            c = read()
        } while (!isSpaceChar(c))
        return res * sgn
    }

    fun isSpaceChar(c: Int): Boolean {
        if (filter != null) {
            return filter.isSpaceChar(c)
        }
        return c == ' '.code || c == '\n'.code || c == '\r'.code || c == '\t'.code || c == -1
    }

    interface SpaceCharFilter {
        fun isSpaceChar(ch: Int): Boolean
    }
}