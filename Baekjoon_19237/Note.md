## 문제
https://www.acmicpc.net/problem/19237

## 문제 설명
문제를 읽기도 이해하기도 힘들었다.
간략화하면 다음과 같다.
1. 맨 처음 맵에 상어의 위치와 상어가 보고 있는 방향이 주어진다.
2. 상어는 보고 있는 방향으로 가는 것이 아닌, 보고 있는 방향에 따라 4방향 중 우선순위가 주어진다.
3. 우선순위에 따라 4방향 중 아직 냄새가 안퍼진 곳이 있다면 그 칸으로 움직인다. 모든 칸에 냄새가 있다면 자신의 냄새가 나는 곳으로 움직인다. 이 또한 우선순위에 따라 이동한다.
4. 만약 모든 상어가 움직인 후, 한 칸에 여러 상어가 있다면 번호가 작은 상어가 그 칸을 차지하며, 나머지 상어는 맵 밖으로 쫓겨난다.
5. 1000초를 초과하면 상어가 한마리든 뭐든 -1을 출력한다. ( 이거땜에 틀렸었음. )
6. 1000초 이내라면 걸린 시간을 출력한다.

## 문제 풀이
문제를 분류해보면 다음과 같다.
1. 입력
2. 상어 움직이기
   2.1 같은 칸에 상어가 있다면 번호가 작은 상어가 칸을 차지
3. 냄새 퍼트리기
4. 시간 체크
5. 상어 갯수 체크

우선적으로 상어 data class를 만들었다.
```
private data class SharkInfo(
    val num: Int,
    val dir: Int = -1,
    val y: Int,
    val x: Int
)
```
이 data class는 상어의 위치를 담는 Queue와 움직인 후 상어의 위치를 담는 List의 객체로 사용이 된다.
맨 처음 맵을 탐색하며 그 칸의 값이 0이 아니라면 Queue에 SharkInfo 객체를 생성하여 넣어준다.

이후 while(true)로 무한 반복을 돌리며 계속해서 과정을 반복하게 된다.
while(sharkIndex.isNotEmpty())로 반복하여 현재 que에 담겨있는 상어들의 정보를 빼와 상어가 보고있는 방향의 우선순위에 따라 움직여준다.
```
private fun priorityForMove(sharkNum: Int, direction: Int, y: Int, x: Int): Triple<Int, Int, Int> {
    val priorityList = movePriority[sharkNum-1][direction-1]

    for(i in priorityList.indices) {
        val d = priorityList[i]
        val nextY = y + dir[d].first
        val nextX = x + dir[d].second

        if(nextY !in 0 until n || nextX !in 0 until n) continue

        if(sharkSmell[nextY][nextX].first == 0 && sharkSmell[nextY][nextX].second == 0) return Triple(d, nextY, nextX)
    }


    for(i in priorityList.indices) {
        val d = priorityList[i]
        val nextY = y + dir[d].first
        val nextX = x + dir[d].second

        if(nextY !in 0 until n || nextX !in 0 until n) continue

        if(sharkSmell[nextY][nextX].first == sharkNum) return Triple(d, nextY, nextX)
    }

    return Triple(-1, -1, -1)
}
```

우선적으로 냄새가 없는 칸으로 움직이며, 냄새가 없는 칸이 없을 시 자신의 냄새가 있는 칸으로 가기에 반복문을 분리하였다.

상어를 움직인 후 그 칸에 다른 상어가 있을 시 번호를 비교해준다.
```
if(map[sharkNextY][sharkNextX] != 0) {
    if(map[sharkNextY][sharkNextX] > sharkCurNum) {
        val filter = moveSharkIndex.filter { it.num != map[sharkNextY][sharkNextX] }
        moveSharkIndex = mutableListOf()
        moveSharkIndex.addAll(filter)

        map[sharkNextY][sharkNextX] = sharkCurNum
        moveSharkIndex.add(SharkInfo(map[sharkNextY][sharkNextX], changeDir, sharkNextY, sharkNextX))
    }
}
else {
    map[sharkNextY][sharkNextX] = sharkCurNum
    moveSharkIndex.add(SharkInfo(map[sharkNextY][sharkNextX], changeDir, sharkNextY, sharkNextX))
}
```

다음으론 냄새를 퍼트린다.
이 과정에서 기존의 칸에 있던 냄새들을 1씩 줄여주고, k초가 지났다면 없애준다.
```
private fun spreadSmell() {
    for(i in 0 until n) {
        for(j in 0 until n) {
            if(sharkSmell[i][j].first != 0) {
                val sharkNum = sharkSmell[i][j].first
                val smellCount = sharkSmell[i][j].second - 1

                sharkSmell[i][j] = if(smellCount == 0) Pair(0, 0) else Pair(sharkNum, smellCount)
            }

            if(map[i][j] != 0) sharkSmell[i][j] = Pair(map[i][j], k)
        }
    }
}
```

마지막으로 1000초가 지났다면 -1을 반환하고, 1000초가 지나지 않고 상어가 1마리 남았다면 시간을 반환한다.
```
private fun solve(): Int {
    findShark()

    var time = 0
    while(true) {
        moveShark()
        spreadSmell()

        time++
        if(time > 1000) return -1

        if(sharkCount() == 1) break
    }

    return time
}
```
