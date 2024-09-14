import java.util.LinkedList
import java.util.Queue

private val buildingInfo: Queue<BuildInfo> = LinkedList()
private lateinit var isAlreadyBuild: BooleanArray
private lateinit var buildTime: IntArray

private data class BuildInfo(
    val num: Int,
    val time: Int,
    val priorityList: List<Int>
)


fun main() {
    input()
    solve()
    output()
}

private fun input() {
    val buildingCnt = readLine()!!.toInt()

    isAlreadyBuild = BooleanArray(buildingCnt + 1)
    buildTime = IntArray(buildingCnt + 1)

    repeat(buildingCnt) { num ->
        val input = readLine()!!.split(" ").map { it.toInt() }
        val time = input[0]
        val priorityList = input.subList(1, input.size - 1)

        buildingInfo.add(BuildInfo(num + 1, time, priorityList))
    }
}

private fun solve() {
    while(buildingInfo.isNotEmpty()) {
        val cur = buildingInfo.poll()

        val curBuildNum = cur.num
        val curBuildTime = cur.time
        val priorityList = cur.priorityList

        if(priorityList.isEmpty()) {
            buildTime[curBuildNum] = curBuildTime
            isAlreadyBuild[curBuildNum] = true
            continue
        }

        var check = true
        var maxTime = 0
        for(building in priorityList) {
            if(isAlreadyBuild[building]) {
                maxTime = maxOf(maxTime, buildTime[building])
            } else {
                check = false
                break
            }
        }

        if(check) {
            buildTime[curBuildNum] = maxTime + curBuildTime
            isAlreadyBuild[curBuildNum] = true
        } else {
            buildingInfo.add(cur)
        }
    }
}

private fun output() {
    for(i in 1 until buildTime.size) {
        println(buildTime[i])
    }
}