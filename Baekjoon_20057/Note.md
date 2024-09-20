## 문제
https://www.acmicpc.net/problem/20057

## 문제 설명
행렬의 중간부터 토네이도가 움직이기 시작한다.
토네이도가 움직일 때마다 각 방향 별로 모래가 일정 비율 퍼져나가게 된다.
행렬의 밖으로 나가는 모래의 양을 구한다.

## 문제 풀이
### 토네이도가 움직이는 방향
- 1칸 > 2번
- 2칸 > 2번
...
- n칸 > 3번

즉, n번째만 3번 움직이고 그 외는 2번씩만 움직인다.

이 방향에 따라 반복문을 돌려주면 됌.

## 각 방향 별 모래가 퍼지는 비율
### 토네이도가 왼쪽으로 움직일 때
```
leftDirRate = arrayOf(
    intArrayOf(-2, 0, 2),
    intArrayOf(-1, -1, 10), intArrayOf(-1, 0, 7), intArrayOf(-1, 1, 1),
    intArrayOf(0, -2, 5), intArrayOf(0, -1, 100),
    intArrayOf(1, -1, 10), intArrayOf(1, 0, 7), intArrayOf(1, 1, 1),
    intArrayOf(2, 0, 2)
)
```
### 오른쪽으로 움직일 때
왼쪽으로 움직일 때의 좌우만 바꿔주면 된다.
```
private fun Array<IntArray>.changeRightRate(): Array<IntArray> {
    return this.map { intArrayOf(it[0], it[1] * -1, it[2]) }.toTypedArray()
}
```
### 윗쪽으로 움직일 때
```
upDirRate = arrayOf(
    intArrayOf(-2, 0, 5),
    intArrayOf(-1, -1, 10), intArrayOf(-1, 0, 100), intArrayOf(-1, 1, 10),
    intArrayOf(0, -2, 2), intArrayOf(0, -1, 7), intArrayOf(0, 1, 7), intArrayOf(0, 2, 2),
    intArrayOf(1, -1, 1), intArrayOf(1, 1, 1)
)
```
### 아랫쪽으로 움직일 때
윗쪽으로 움직일 때의 상하만 바꿔주면 된다.
```
private fun Array<IntArray>.changeDownRate(): Array<IntArray> {
    return this.map { intArrayOf(it[0] * -1, it[1], it[2]) }.toTypedArray()
}
```

움직인 칸이 행렬을 벗어난다면 벗어난 양을 정의한 변수에 더해주고, 아니라면 행렬의 위치에 더해준다.
```
if(nextY !in 0 until n || nextX !in 0 until n) {
    outMapSand += partialSand
} else {
    map[nextY][nextX] += partialSand
}
```  

모래를 다 움직인 후에 기존 모래가 있던 칸에는 토네이도가 있게 되므로 모래의 양을 0으로 설정해준다.
